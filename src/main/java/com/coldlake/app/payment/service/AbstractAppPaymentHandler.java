package com.coldlake.app.payment.service;

import com.coldlake.app.payment.domain.PaymentOrder;
import com.coldlake.app.payment.domain.PaymentOrderCreation;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 15:38
 */

public abstract class AbstractAppPaymentHandler extends AbstractPaymentHandler {

    public static final String ABSTRACT_APP_PAYMENT_HANDLER = "ABSTRACT_APP_PAYMENT_HANDLER";

    @Override
    public PaymentOrder createOrder(PaymentOrderCreation p) throws Exception {
        PaymentOrder res = new PaymentOrder();
        res.setSubscribeId("");
        res.setPaymentUrl(p.getOrderId());
        return res;
    }

}
