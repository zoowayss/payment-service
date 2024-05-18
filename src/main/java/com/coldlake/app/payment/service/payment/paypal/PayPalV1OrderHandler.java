//package com.coldlake.app.payment.service.paypal;
//
//import com.coldlake.app.payment.domain.payment.PaymentOrder;
//import com.coldlake.app.payment.domain.enums.PaymentOrderStatusEnum;
//import com.coldlake.app.payment.domain.payment.paypal.subscription.Subscription;
//import org.springframework.stereotype.Service;
//
///**
// * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
// * @Date: 2024/4/22 18:59
// */
//@Service
//public class PayPalV1OrderHandler extends AbstractPayPalOrderHandler {
//    public static final String PAYPAL_V1_ORDER = "paypalV1Order";
//
//    @Override
//    public PaymentOrder retrieveOrder(String tradeNo) throws Exception {
//        Subscription subscription = paypalClient.agreementExecute(tradeNo);
//        return new PaymentOrder(subscription.getId(), PAY_TYPE_PAYPAL, subscription.getId(), PaymentOrderStatusEnum.of(subscription.getStatus()), "", 0L);
//    }
//
//    @Override
//    public String getName() {
//        return PAYPAL_V1_ORDER;
//    }
//
//
//    @Override
//    public int getPayType() {
//        return PAY_TYPE_PAYPAL;
//    }
//}
