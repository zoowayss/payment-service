package com.coldlake.app.payment.job;

import com.alicp.jetcache.AutoReleaseLock;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.coldlake.app.payment.domain.UserToken;
import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.entity.PaymentChargeOrder;
import com.coldlake.app.payment.service.PaymentUserService;
import com.coldlake.app.payment.service.payment.PaymentOrderHandler;
import com.coldlake.app.payment.service.payment.PaymentOrderService;
import com.coldlake.app.payment.service.payment.listener.PaymentSuccessPayOnlyExecOnceListener;
import com.coldlake.app.payment.utils.JsonUtils;
import com.coldlake.app.payment.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.coldlake.app.payment.service.payment.LogService.cm;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/15 11:27
 */
@Slf4j
public class PaymentOrderJob {


    /**
     * 需要的定时任务：
     * 1。 查单，查询用户订单是未支付状态 && 没有过期 的订单，去第三方支付平台查询订单状态，如果是已支付状态，更新订单状态
     * 2。 查询将要过期的会员的订单，拿到订阅id，去第三方平台查询订阅状态，如果是取消状态，更新订单状态，如果是 active 状态，给他延长会员时间
     * 3。 升级会员之后，取消会员，可能失败，需要定时去取消用户的会员（eg：https证书过期，导致 stripe 回调失败，不能更新订单的 subscription_id,用户升级会员取消之前订阅时，拿到的 subscription_id 是 ''）
     */

    private Map<String, PaymentOrderHandler> paymentOrderFactory;

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private List<PaymentSuccessPayOnlyExecOnceListener> successPayOnlyExecOnceListenerList;

    @Resource
    private PaymentUserService paymentUserService;

    @CreateCache(name = "syncExchange", expire = 10, cacheType = CacheType.REMOTE)
    private Cache<String, Boolean> cacheLock;

    public PaymentOrderJob(List<PaymentOrderHandler> paymentOrderHandlers) {
        this.paymentOrderFactory = paymentOrderHandlers.stream().collect(Collectors.toMap(PaymentOrderHandler::getName, Function.identity()));
    }

    /**
     * 未支付订单检查
     * 默认10分钟执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void checkUnpaidOrder() {
        String cm = cm();
        try (AutoReleaseLock lock = cacheLock.tryLock(cm, 60, TimeUnit.SECONDS)) {
            if (lock == null) {
                log.info(cm + " executeKey:{} is locked", cm);
                return;
            }
            this.doCheckUnpaidOrder();
        }
    }

    public void doCheckUnpaidOrder() {
        String cm = cm();
        log.info("{} start....", cm);
        long start = System.currentTimeMillis();

        // 查询用户订单是 未支付状态 的订单
        List<PaymentChargeOrder> unpaidOrders = paymentOrderService.listUnpaidOrder();
        for (PaymentChargeOrder unpaidOrder : unpaidOrders) {
            try {

                if (isExpiredOrder(unpaidOrder.getExpireTime())) {
                    log.info("{} order {} is expired ", cm, unpaidOrder.getTradeNo());
                    boolean res = paymentOrderService.casOrderStatus(unpaidOrder.getId(), unpaidOrder.getStatus(), PaymentOrderStatusEnum.EXPIRED.getIdx());
                    log.info("{} order {} status change from {} to {} {}", cm, unpaidOrder.getTradeNo(), unpaidOrder.getStatus(), PaymentOrderStatusEnum.EXPIRED, res ? "successful" : "failed");
                    continue;
                }

                PaymentOrderHandler paymentOrderHandler = Optional.ofNullable(paymentOrderFactory.get(unpaidOrder.getOrderType()))
                        .orElseThrow(() -> new RuntimeException("not found PaymentOrderManager for " + unpaidOrder.getOrderType()));
                PaymentOrder newStatusPaymentOrder = paymentOrderHandler.retrieveOrderWithListener(unpaidOrder.getTradeNo());

                if (newStatusPaymentOrder.getStatus() == PaymentOrderStatusEnum.ACTIVE) {
                    // 第三方支付成功，需要根据时间来判断 是否已经给会员加时间了，如果没有加时间，需要给会员加时间
                    if (!paymentOrderService.casOrderStatus(unpaidOrder.getId(), unpaidOrder.getStatus(), newStatusPaymentOrder.getStatus()
                            .getIdx())) {
                        log.info("{} order status has been updated by other thread order:{}", cm(), JsonUtils.logJson(unpaidOrder));
                    }
                    UserToken userToken = paymentUserService.generateToken(unpaidOrder.getUid());
                    unpaidOrder.setUid(userToken.getUid());
                    for (PaymentSuccessPayOnlyExecOnceListener listener : successPayOnlyExecOnceListenerList) {
                        listener.onSuccessPay(unpaidOrder);
                    }
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    log.warn("{} notfound subscription in paypal. order:{}", cm, unpaidOrder);
                    boolean res = paymentOrderService.casOrderStatus(unpaidOrder.getTradeNo(), PaymentOrderStatusEnum.CREATE.getIdx(), PaymentOrderStatusEnum.SUB_NOTFOUND.getIdx());
                    log.info("{} order {} status change from {} to {} {}", cm, unpaidOrder.getTradeNo(), PaymentOrderStatusEnum.CREATE.getIdx(), PaymentOrderStatusEnum.SUB_NOTFOUND.getIdx(), res ? "successful" : "failed");
                    continue;
                }
                log.error("{} error", cm, e);
            } catch (Exception e) {
                log.error("{} error. order:{}", cm, unpaidOrder);
            }
        }
        log.info(cm + " end cost: {} ms", System.currentTimeMillis() - start);
    }

    private boolean isExpiredOrder(Long expireTime) {
        return TimeUtils.getCurrentTimeMils() >= expireTime;
    }

}
