//package com.coldlake.app.payment.job;
//
//import com.alicp.jetcache.AutoReleaseLock;
//import com.alicp.jetcache.Cache;
//import com.alicp.jetcache.anno.CacheType;
//import com.alicp.jetcache.anno.CreateCache;
//import com.coldlake.app.payment.domain.PaymentOrder;
//import com.coldlake.app.payment.domain.enums.PaymentOrderStatusEnum;
//import com.coldlake.app.payment.utils.TimeUtils;
//import com.coldlake.app.payment.service.PaymentOrderHandler;
//import com.coldlake.app.payment.service.PaymentOrderService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.util.StringUtils;
//import org.springframework.web.client.HttpClientErrorException;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
// * @Date: 2024/4/15 11:27
// */
//@Slf4j
//public class PaymentOrderJob {
//
//
//    /**
//     * 需要的定时任务：
//     * 1。 查单，查询用户订单是未支付状态 && 没有过期 的订单，去第三方支付平台查询订单状态，如果是已支付状态，更新订单状态
//     * 2。 查询将要过期的会员的订单，拿到订阅id，去第三方平台查询订阅状态，如果是取消状态，更新订单状态，如果是 active 状态，给他延长会员时间
//     * 3。 升级会员之后，取消会员，可能失败，需要定时去取消用户的会员（eg：https证书过期，导致 stripe 回调失败，不能更新订单的 subscription_id,用户升级会员取消之前订阅时，拿到的 subscription_id 是 ''）
//     */
//
//    private Map<String, PaymentOrderHandler> paymentOrderFactory;
//
//    @Autowired
//    public PaymentOrderJob(List<PaymentOrderHandler> paymentOrderHandlers, PaymentOrderService paymentOrderService) {
//        this.paymentOrderFactory = paymentOrderHandlers.stream().collect(Collectors.toMap(PaymentOrderHandler::getName, Function.identity()));
//        this.paymentOrderService = paymentOrderService;
//    }
//
//    private PaymentOrderService paymentOrderService;
//
//    @CreateCache(name = "syncExchange", expire = 10, cacheType = CacheType.REMOTE)
//    private Cache<String, Boolean> cacheLock;
//
//    /**
//     * 未支付订单检查
//     * 默认10分钟执行一次
//     */
//    @Scheduled(cron = "0 0/10 * * * ?")
//    public void checkUnpaidOrder() {
//        String cm = "checkUnpaidOrder@PaymentOrderJob";
//        try (AutoReleaseLock lock = cacheLock.tryLock(cm, 60, TimeUnit.SECONDS)) {
//            if (lock == null) {
//                log.info(cm + " executeKey:{} is locked", cm);
//                return;
//            }
//            this.doCheckUnpaidOrder();
//        }
//    }
//
//    public void doCheckUnpaidOrder() {
//        String cm = "doCheckUnpaidOrder@PaymentOrderJob";
//        log.info(cm + " start ");
//        long start = System.currentTimeMillis();
//
//        // 查询用户订单是 未支付状态 的订单
//        List<PaymentOrder> unpaidOrders = paymentOrderService.listUnpaidOrder();
//        for (PaymentOrder unpaidOrder : unpaidOrders) {
//            try {
//
//                if (isExpiredOrder(unpaidOrder.getExpireTime())) {
//                    log.info(cm + " order {} is expired ", unpaidOrder.getTradeNo());
//                    boolean res = paymentOrderService.casOrderStatus(unpaidOrder.getTradeNo(), "", unpaidOrder.getStatus().getIdx(), PaymentOrderStatusEnum.EXPIRED.getIdx());
//                    log.info(cm + " order {} status change from {} to {} {}", unpaidOrder.getTradeNo(), unpaidOrder.getStatus(), PaymentOrderStatusEnum.EXPIRED, res ? "successful" : "failed");
//                    continue;
//                }
//
//                PaymentOrderHandler paymentOrderHandler = Optional.ofNullable(paymentOrderFactory.get(unpaidOrder.getSupportPaymentOrderManager()))
//                        .orElseThrow(() -> new RuntimeException("not found PaymentOrderManager for " + unpaidOrder.getSupportPaymentOrderManager()));
//                PaymentOrder newStatusPaymentOrder = paymentOrderHandler.retrieveOrderWithListener(unpaidOrder.getTradeNo());
//
//                if (newStatusPaymentOrder.getStatus() == PaymentOrderStatusEnum.ACTIVE || newStatusPaymentOrder.getStatus() == PaymentOrderStatusEnum.CANCELED) {
//                    // 第三方支付成功，需要根据时间来判断 是否已经给会员加时间了，如果没有加时间，需要给会员加时间
//                    String tradeNo = paymentOrderService.queryTradeNoBySubscriptionId(newStatusPaymentOrder.getSubscribeId());
//                    if (StringUtils.isEmpty(tradeNo)) throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("%s tradeNo is empty", cm));
//                    paymentOrderService.paymentOrderUpdateOrderPaidStatusIfNecessary(tradeNo, paymentOrderHandler);
//                    continue;
//                }
//
//                log.info(cm + " order {} is still not paid ", unpaidOrder.getTradeNo());
//            } catch (HttpClientErrorException e) {
//                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
//                    log.warn("{} notfound subscription in paypal. order:{}", cm, unpaidOrder);
//                    boolean res = paymentOrderService.casOrderStatus(unpaidOrder.getTradeNo(), "", PaymentOrderStatusEnum.CREATE.getIdx(), PaymentOrderStatusEnum.SUB_NOTFOUND.getIdx());
//                    log.info("{} order {} status change from {} to {} {}", cm, unpaidOrder.getTradeNo(), PaymentOrderStatusEnum.CREATE.getIdx(), PaymentOrderStatusEnum.SUB_NOTFOUND.getIdx(), res ? "successful" : "failed");
//                    continue;
//                }
//                log.error("{} error", cm, e);
//            } catch (Exception e) {
//                log.error("{} error. order:{}", cm, unpaidOrder);
//            }
//        }
//        log.info(cm + " end cost: {} ms", System.currentTimeMillis() - start);
//    }
//
//    private boolean isExpiredOrder(Long expireTime) {
//        return TimeUtils.getCurrentTimeMils() >= expireTime;
//    }
//
//
//    /**
//     * 会员续期失败任务
//     * 默认一分钟执行一次
//     */
////    @Scheduled(cron = "0 0/15 * * * ?")
//    public void checkRenewMembersJob() {
//        String cm = "checkRenewMembersJob@PaymentOrderJob";
//        try (AutoReleaseLock lock = cacheLock.tryLock(cm, 60, TimeUnit.SECONDS)) {
//            if (lock == null) {
//                log.info(cm + " executeKey:{} is locked", cm);
//                return;
//            }
//            this.doCheckRenewMembersJob();
//        }
//    }
//
//    private void doCheckRenewMembersJob() {
//        String cm = "doCheckRenewMembersJob@PaymentOrderJob";
//        log.info(cm + " start ");
//        long start = System.currentTimeMillis();
//
//        List<PaymentOrder> willExpiredMemberList = paymentOrderService.listWillExpiredMembers();
//
//        for (PaymentOrder willExpiredMemberOrder : willExpiredMemberList) {
//            try {
//                PaymentOrder newStatusPaymentOrder = Optional.ofNullable(paymentOrderFactory.get(willExpiredMemberOrder.getSupportPaymentOrderManager()))
//                        .orElseThrow(() -> new RuntimeException("not found PaymentOrderManager for " + willExpiredMemberOrder.getSupportPaymentOrderManager())).retrieveOrderWithListener(willExpiredMemberOrder.getTradeNo());
//
//                if (newStatusPaymentOrder.getStatus() == PaymentOrderStatusEnum.ACTIVE) {
//                    // 第三方成功给会员续费，需要根据时间来判断 是否已经给会员加时间了，如果没有加时间，需要给会员加时间
//                    paymentOrderService.updateUserSubscriptionIfNecessary(newStatusPaymentOrder.getSubscribeId(), null, newStatusPaymentOrder.getLastPaymentTime());
//                }
//            } catch (Exception e) {
//                log.error(cm + " handle order excepted error . id or subscription_id: {}", willExpiredMemberOrder.getTradeNo(), e);
//            }
//        }
//        log.info(cm + " end cost: {} ms", System.currentTimeMillis() - start);
//    }
//
//
//    /**
//     * 默认30分钟执行一次
//     */
////    @Scheduled(cron = "0 0/30 * * * ?")
//    public void cancelSubscriptionFailedJob() {
//        String cm = "cancelSubscriptionFailedJob@PaymentOrderJob";
//        try (AutoReleaseLock lock = cacheLock.tryLock(cm, 60, TimeUnit.SECONDS)) {
//            if (lock == null) {
//                log.info(cm + " executeKey:{} is locked", cm);
//                return;
//            }
//            this.doCancelSubscriptionFailedJob();
//        }
//    }
//
//    private void doCancelSubscriptionFailedJob() {
//        String cm = "doCancelSubscriptionFailedJob@PaymentOrderJob";
//        log.info(cm + " start ");
//        long start = System.currentTimeMillis();
//        // 查出 会员升级之后，取消订阅，可能失败的订单
//        List<PaymentOrder> cancelSubscriptionFailedOrderList = paymentOrderService.listCancelSubscriptionFailedOrders();
//
//        for (PaymentOrder cancelSubscriptionFailedOrder : cancelSubscriptionFailedOrderList) {
//            try {
//                paymentOrderFactory.get(cancelSubscriptionFailedOrder.getSupportPaymentOrderManager()).cancelSubscriptionWithListener(cancelSubscriptionFailedOrder.getTradeNo(),
//                        "Last time cancel failed. need to cancel again");
//            } catch (Exception e) {
//                log.error(cm + " cancel subscription again error ! tradeNo {} ", cancelSubscriptionFailedOrder.getTradeNo(), e);
//            }
//        }
//        log.info(cm + " end cost: {} ms ", System.currentTimeMillis() - start);
//
//    }
//
//}
