package com.coldlake.app.payment.service.payment.callback;

import com.coldlake.app.payment.domain.payment.IInvoice;
import com.coldlake.app.payment.domain.payment.paypal.PayPalWebhookEvent;
import com.stripe.model.Charge;
import com.stripe.model.Invoice;
import com.stripe.model.PaymentIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 09:21
 */
public interface PaymentCallbackHandler {

    Logger log = LoggerFactory.getLogger(PaymentCallbackHandler.class);

    /**
     * 处理Stripe Checkout Session Completed Event
     *
     * @param orderId
     * @param mode
     */
    default void handleStripeCheckoutSessionCompletedEvent(String orderId, String mode) {
    }

    /**
     * 处理Stripe Invoice Paid Event
     *
     * @param subscriptionId
     * @param invoice
     * @param paidAt
     */
    default void handleStripeInvoicePaidEvent(String subscriptionId, IInvoice invoice, Long paidAt) {
    }


    /**
     * 处理Paypal Subscription 支付成功
     *
     * @param subscribeId
     * @param resource
     */
    default void handlePaymentSuccessPay(String subscribeId, PayPalWebhookEvent.Resource resource) {
    }

    /**
     * 处理Paypal Subscription 续期成功
     *
     * @param tradeNo
     * @param resourceId
     * @param createTime
     * @param resource
     */
    default void handlePayPalPaymentSaleCompleted(String tradeNo, IInvoice resourceId, String createTime, PayPalWebhookEvent.Resource resource) {
    }

    /**
     * 处理Paypal Subscription 取消
     *
     * @param subscriptionId
     */
    default void handleBillingSubscriptionCancelled(String subscriptionId) {
    }

    /**
     * 处理Stripe Customer Subscription Updated Event，主要是将 新的 invoice 和 subscriptionId 绑定
     *
     * @param subscriptionId
     * @param latestInvoiceId
     */
    default void handleStripeCustomSubscriptionUpdatedEvent(String subscriptionId, String latestInvoiceId) {
    }

    /**
     * 处理Stripe Payment Intent Payment Failed Event
     * 根据 invoice 找到对应的订单，填充 code  declineCode
     *
     * @param invoice
     * @param code
     * @param declineCode
     */
    default void handleStripePaymentIntentPaymentFailedEvent(String invoice, String code, String declineCode) {
    }


    default void handleStripePaymentIntentCreated(PaymentIntent pi) {
    }

    default void handleStripePaymentIntentSucceeded(Invoice ips) {
    }

    default void handleStripeChargeSucceeded(Charge charge) {
    }

    default void handleStripeInvoiceCreatedEvent(Invoice invoice) {
    }

    default void handleAppleSubscriptionNotify(String inReq) {
    }
}
