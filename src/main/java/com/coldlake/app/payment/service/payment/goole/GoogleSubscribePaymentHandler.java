package com.coldlake.app.payment.service.payment.goole;

import com.coldlake.app.payment.domain.payment.AppRetrieveOrderWrapper;
import com.coldlake.app.payment.domain.payment.PaymentOrder;
import org.springframework.stereotype.Service;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 15:43
 */
@Service
public class GoogleSubscribePaymentHandler extends AbstractGooglePaymentOrderHandler {
    public static final String GOOGLE_SUBSCRIBE = "GOOGLE_SUBSCRIBE";

    @Override
    public String getName() {
        return GOOGLE_SUBSCRIBE;
    }


    @Override
    public PaymentOrder successPay(AppRetrieveOrderWrapper orderWrapper) throws Exception {
        // 去google服务验证订单
        //        SubscriptionPurchase purchase = googleClient.getGoogleSubscription(orderWrapper.getOrderId(), orderWrapper.getProductId(),
        //                orderWrapper.getPurchaseToken());
        //        if (!purchase.getObfuscatedExternalAccountId().equals(orderWrapper.getOrderId())) {
        //            throw new RuntimeException("Order mismatch");
        //        }
        //        // 订阅的付款状态。可能的值包括：0。付款待处理 1. 付款已收讫 2. 免费试用 3. 待推迟升级/降级
        //        // 不适用于已取消、已过期的订阅。
        //        List<Integer> paymentStateList = Arrays.asList(1, 2);
        //        // 判断是否已经支付
        //        if (purchase.getPaymentState() == null || !paymentStateList.contains(purchase.getPaymentState())) {
        //            throw new RuntimeException("Please pay first");
        //        }
        //
        //        // 如果没有确认则先认证订单
        //        if (!purchase.getAcknowledgementState().equals(1)) {
        //            googleClient.acknowledgeSubscription(orderWrapper.getOrderId(), orderWrapper.getProductId(), orderWrapper.getPurchaseToken());
        //        }
        //
        //        PaymentOrder res = new PaymentOrder();
        //        res.setTradeNo(purchase.getOrderId());
        //        res.setLastPaymentTime(purchase.getStartTimeMillis());
        //        return res;
        return null;
    }
}
