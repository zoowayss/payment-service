package com.coldlake.app.payment.service.payment;

import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.PaymentOrderCreation;
import com.coldlake.app.payment.service.payment.listener.PaymentOrderListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/15 08:57
 */

public abstract class AbstractPaymentHandler implements PaymentOrderHandler {

    public static final String NAME = "AbstractThirdPartManager";

    @Resource
    private List<PaymentOrderListener> paymentOrderListeners;

    /**
     * 创建支付订单
     *
     * @param p
     * @return
     */
    protected abstract PaymentOrder createOrder(PaymentOrderCreation p) throws Exception;

    @Transactional
    @Override
    public PaymentOrder createPaymentOrder(PaymentOrderCreation paymentOrderCreation, Object optional) throws Exception {
        paymentOrderListeners.forEach(processor -> processor.processPaymentOrderPreCreate(optional));
        PaymentOrder res = createOrder(paymentOrderCreation);
        paymentOrderListeners.forEach(processor -> processor.processPaymentOrderCreated(res, optional));
        return res;
    }

    @Transactional
    @Override
    public void cancelSubscriptionWithListener(String subscriptionId, String reason) throws Exception {
        cancelSubscription(subscriptionId, reason);
        paymentOrderListeners.forEach(paymentSubscriptionCanceledPostProcessor -> paymentSubscriptionCanceledPostProcessor.processSubscriptionBeCanceled(subscriptionId));
    }


    @Transactional
    @Override
    public PaymentOrder retrieveOrderWithListener(String tradeNo) throws Exception {
        PaymentOrder resOrder = retrieveOrder(tradeNo);
        paymentOrderListeners.forEach(paymentOrderRetrieveOrderPostProcessor -> paymentOrderRetrieveOrderPostProcessor.processPostRetrieveOrder(resOrder, getName()));
        return resOrder;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
