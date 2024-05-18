package com.coldlake.app.payment.controller;

import com.alicp.jetcache.AutoReleaseLock;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.coldlake.app.payment.controller.domain.ex.BusinessException;
import com.coldlake.app.payment.controller.domain.req.CreateOrderReq;
import com.coldlake.app.payment.controller.domain.req.SuccessPayReq;
import com.coldlake.app.payment.controller.domain.res.SuccessPayRes;
import com.coldlake.app.payment.controller.domain.vo.ResultVo;
import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.ProcessParam;
import com.coldlake.app.payment.domain.payment.SkuConfItem;
import com.coldlake.app.payment.domain.payment.constants.Constants;
import com.coldlake.app.payment.domain.payment.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.domain.payment.enums.PaymentTypeEnum;
import com.coldlake.app.payment.service.payment.NamedHandler;
import com.coldlake.app.payment.service.payment.PaymentOrderHandler;
import com.coldlake.app.payment.service.payment.PaymentOrderService;
import com.coldlake.app.payment.utils.RandStrUtils;
import com.coldlake.app.payment.utils.ServletUtils;
import com.coldlake.app.payment.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.coldlake.app.payment.service.payment.LogService.cm;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/17 18:04
 */
@RestController
@Slf4j
@RequestMapping("/{version}/{bName}/order")
public class PaymentController {


    @Resource
    private PaymentOrderService paymentOrderService;

    private Map<String, PaymentOrderHandler> paymentOrderHandlerFactory;

    @CreateCache(name = "syncExchange", expire = 10, cacheType = CacheType.REMOTE)
    private Cache<String, Boolean> cacheLock;

    @Resource
    public void setPaymentOrderHandlerFactory(List<PaymentOrderHandler> paymentOrderHandlerList) {
        this.paymentOrderHandlerFactory = paymentOrderHandlerList.stream()
                .collect(Collectors.toMap(NamedHandler::getName, Function.identity()));
    }

    /**
     * curl -X POST http://localhost:59441/v1/calorie/order/2?sku=lifetime
     *
     * @param type
     * @param orderReq
     * @param request
     * @return
     */
    @PostMapping("/{type}")
    public ResultVo<?> createOrder(@PathVariable("version") String ignore,
                                   @PathVariable String bName,
                                   @PathVariable Integer type,
                                   CreateOrderReq orderReq,
                                   HttpServletRequest request) {
        String cm = cm();
        PaymentTypeEnum paymentTypeEnum = Optional.ofNullable(PaymentTypeEnum.ofIndex(type))
                .orElseThrow(() -> new BusinessException(String.format("not support payment type: %d", type)));

        PaymentOrderHandler paymentOrderHandler = Optional.ofNullable(paymentOrderHandlerFactory.get(paymentTypeEnum.getDesc()))
                .orElseThrow(() -> new RuntimeException(String.format("%s not found payment handler via :%s", cm(), paymentTypeEnum.getDesc())));

        // 验证 sku
        SkuConfItem skuConfItem = paymentOrderService.validateSkuInRequest(orderReq.getSku(), true, orderReq.getSource());

        String did = ServletUtils.appDeviceId(request);
        // 防止帕金森用户下单
        String lockKey = String.format("create:order:%s:%s", skuConfItem.getSkuId(), did);
        try (AutoReleaseLock lock = cacheLock.tryLock(lockKey, 60, TimeUnit.SECONDS)) {
            if (lock == null) {
                log.info("{} executeKey:{} is locked", cm(), lockKey);
                throw new BusinessException("Too many requests");
            }
            String orderId = RandStrUtils.generateOrderId(String.valueOf(System.currentTimeMillis()));
            // 回调携带参数
            String chan = ServletUtils.appChannel(request);
            ProcessParam processParam = ProcessParam.builder()
                    .uid(0L)
                    .did(did)
                    .clientIp(ServletUtils.getClientIp(request))
                    .clientUA(ServletUtils.getUA(request))
                    .clientSys(ServletUtils.appClientSys(request))
                    .duration(skuConfItem.getDuration())
                    .fbc(orderReq.getFbc())
                    .eventSourceUrl(StringUtils.hasText(request.getQueryString()) ? (request.getRequestURL() + "?" + request.getQueryString()) : (request.getRequestURL()
                            .toString()))
                    .chan(chan)
                    .orderType(paymentOrderHandler.getName())

                    .skuId(skuConfItem.getSkuId())
                    .skuName(skuConfItem.getName())
                    .amount(skuConfItem.getAmount())
                    .cycle(skuConfItem.getCycle())
                    .bid(bName)
                    .currency(Constants.USD)
                    .quantity(1L)
                    .orderId(orderId)
                    .host(ServletUtils.getRequestPrefix(request))
                    .expiresAt(TimeUtils.getCurrentTimeMils() + Constants.HALF_HOUR_MILLIS)
                    .successUrlParams(String.format(paymentOrderHandler.getSuccessUri(), bName, type, chan, did, orderReq.getFbc(), skuConfItem.getAmount(), orderId))
                    .cancelUrlParams(String.format(paymentOrderHandler.getFailureUri(), chan))
                    .build();

            return ResultVo.asSuccess(paymentOrderService.doCreatePayOrder(paymentOrderHandler, processParam));
        } catch (Exception e) {
            log.error("{}", cm, e);
            throw new BusinessException("Fail to create order", e);
        }
    }

    @GetMapping("/{payType}/success/pay")
    public void successPay(@PathVariable("version") String ignore,
                           @PathVariable Integer payType,
                           @PathVariable String bName,
                           SuccessPayReq successPayReq,
                           HttpServletResponse response) {
        if (!StringUtils.hasText(successPayReq.getTradeNo())) {
            throw new BusinessException("not found tradeNo");
        }

        PaymentTypeEnum paymentTypeEnum = Optional.ofNullable(PaymentTypeEnum.ofIndex(payType))
                .orElseThrow(() -> new RuntimeException(String.format("not found PaymentTypeEnum via type: %d", payType)));

        PaymentOrderHandler paymentOrderHandler = Optional.ofNullable(paymentOrderHandlerFactory.get(paymentTypeEnum.getDesc()))
                .orElseThrow(() -> new RuntimeException(String.format("not  found paymentHandler via :%s", paymentTypeEnum.getDesc())));

        try {
            PaymentOrder paymentOrder = paymentOrderHandler.retrieveOrder(successPayReq.getTradeNo());

            if (PaymentOrderStatusEnum.ACTIVE == paymentOrder.getStatus()) {
                SuccessPayRes successPayRes = paymentOrderService.doSuccessPay(successPayReq, paymentOrderHandler);
                response.sendRedirect(String.format("/success?chan=%s&token=%s", successPayReq.getChan(), successPayRes.getToken()));
            }

        } catch (Exception e) {
            log.error("{}", cm(), e);
            throw new RuntimeException(e);
        }
    }
}
