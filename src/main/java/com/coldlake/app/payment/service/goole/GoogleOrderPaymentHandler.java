package com.coldlake.app.payment.service.goole;

import com.coldlake.app.payment.domain.AppRetrieveOrderWrapper;
import com.coldlake.app.payment.domain.PaymentOrder;
import org.springframework.stereotype.Service;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 15:42
 */
@Service
public class GoogleOrderPaymentHandler extends AbstractGooglePaymentOrderHandler {
    public static final String GOOGLE_ORDER = "GOOGLE_ORDER";

    @Override
    public String getName() {
        return GOOGLE_ORDER;
    }

    @Override
    public PaymentOrder successPay(AppRetrieveOrderWrapper wrapper) throws Exception {


//        ProductPurchase purchase = googleClient.getGoogleOrderInfo(wrapper.getOrderId(), wrapper.getProductId(),
//                wrapper.getPurchaseToken());
//        if (!purchase.getObfuscatedExternalAccountId().equals(wrapper.getOrderId())) {
//            throw new RuntimeException("Order mismatch");
//        }
//
//        // 判断是否已经支付
//        if (!purchase.getPurchaseState().equals(0)) {
//            throw new RuntimeException("Please pay first");
//        }
//
//        // 如果没有确认则先认证订单
//        if (!purchase.getAcknowledgementState().equals(1)) {
//            googleClient.acknowledgeProducts(wrapper.getOrderId(), wrapper.getProductId(), wrapper.getPurchaseToken());
//        }
//
//        PaymentOrder res = new PaymentOrder();
//
//        res.setTradeNo(purchase.getOrderId());
//        res.setLastPaymentTime(purchase.getPurchaseTimeMillis());
//
//        return res;
        return null;
    }
}
