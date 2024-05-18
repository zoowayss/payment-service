package com.coldlake.app.payment.service.payment;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coldlake.app.payment.controller.domain.ex.BusinessException;
import com.coldlake.app.payment.controller.domain.req.SuccessPayReq;
import com.coldlake.app.payment.controller.domain.res.SuccessPayRes;
import com.coldlake.app.payment.controller.domain.vo.CreateOrderVo;
import com.coldlake.app.payment.domain.UserToken;
import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.PaymentOrderCreation;
import com.coldlake.app.payment.domain.payment.ProcessParam;
import com.coldlake.app.payment.domain.payment.SkuConfItem;
import com.coldlake.app.payment.domain.payment.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.entity.PaymentChargeOrder;
import com.coldlake.app.payment.service.PaymentChargeOrderService;
import com.coldlake.app.payment.service.PaymentUserService;
import com.coldlake.app.payment.service.payment.listener.PaymentSuccessPayOnlyExecOnceListener;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.coldlake.app.payment.service.payment.LogService.cm;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/19 12:16
 */
@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {
    @Resource
    @Lazy
    private List<PaymentSuccessPayOnlyExecOnceListener> successPayOnlyExecOnceListenerList;

    @Resource
    private PaymentChargeOrderService chargeOrderService;

    @Resource
    private PaymentUserService paymentUserService;

    @Override
    public boolean casOrderStatus(String paymentChargeOrderId, Integer oldStatus, Integer newStatus) {
        return chargeOrderService.casOrderStatus(paymentChargeOrderId, oldStatus, newStatus);
    }

    @Override
    public List<PaymentChargeOrder> listUnpaidOrder() {
        return chargeOrderService.list(new LambdaQueryWrapper<PaymentChargeOrder>().eq(PaymentChargeOrder::getStatus, PaymentOrderStatusEnum.CANCELED)
                .orderBy(true, false, PaymentChargeOrder::getCreateTime));
    }

    @Override
    public SkuConfItem validateSkuInRequest(String sku, boolean isDev, Integer source) {
        return chargeOrderService.validateSkuInRequest(sku, isDev, source);
    }

    @Override
    public SuccessPayRes doSuccessPay(SuccessPayReq successPayReq, PaymentOrderHandler paymentOrderHandler) {
        String cm = cm();
        PaymentChargeOrder order = chargeOrderService.getByTradeNo(successPayReq.getTradeNo());
        UserToken userToken = paymentUserService.generateToken(order.getUid());
        if (Objects.equals(PaymentOrderStatusEnum.ACTIVE.getIdx(), order.getStatus())) {
            log.info("{} order has paid", cm);
            return SuccessPayRes.builder().token(userToken.getToken()).build();
        }

        if (!chargeOrderService.casOrderStatus(order.getId(), order.getStatus(), PaymentOrderStatusEnum.ACTIVE.getIdx())) {
            log.info("{} order status has been updated by other thread. current thread:{}", cm, Thread.currentThread().getName());
            return SuccessPayRes.builder().token(userToken.getToken()).build();
        }

        String token = userToken.getToken();
        order.setUid(userToken.getUid());
        for (PaymentSuccessPayOnlyExecOnceListener listener : successPayOnlyExecOnceListenerList) {
            listener.onSuccessPay(order);
        }

        return SuccessPayRes.builder().token(token).build();
    }

    @Override
    public CreateOrderVo doCreatePayOrder(PaymentOrderHandler paymentOrderHandler, ProcessParam processParam) {
        PaymentOrderCreation orderCreation = new PaymentOrderCreation();
        BeanUtils.copyProperties(processParam, orderCreation);
        try {
            PaymentOrder order = paymentOrderHandler.createPaymentOrder(orderCreation, processParam);
            return CreateOrderVo.builder().orderId(processParam.getOrderId()).href(order.getPaymentUrl()).build();
        } catch (Exception e) {
            throw new BusinessException("Fail to create order", e);
        }
    }
}
