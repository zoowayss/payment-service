package com.coldlake.app.payment.service.payment.callback;

import com.coldlake.app.payment.domain.payment.IInvoice;
import com.coldlake.app.payment.domain.payment.paypal.PayPalWebhookEvent;
import com.stripe.model.Charge;
import com.stripe.model.Invoice;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 09:25
 */
@Component
public class PaymentCallbackHandlerWrapper {

    @Resource
    private List<PaymentCallbackHandler> paymentCallbackHandlers;


    /**
     * 处理Stripe Checkout Session Completed Event
     *
     * @param orderId
     * @param mode
     */
    @Transactional
    public void handleStripeCheckoutSessionCompletedEvent(String orderId, String mode) {
        paymentCallbackHandlers.forEach(paymentCallbackHandler -> paymentCallbackHandler.handleStripeCheckoutSessionCompletedEvent(orderId, mode));
    }

    /**
     * 处理Stripe Invoice Paid Event
     *
     * @param subscriptionId
     * @param invoice
     * @param paidAt
     */
    @Transactional
    public void handleStripeInvoicePaidEvent(String subscriptionId, Invoice invoice, Long paidAt) {
        paymentCallbackHandlers.forEach(paymentCallbackHandler -> paymentCallbackHandler.handleStripeInvoicePaidEvent(subscriptionId, new IInvoice(invoice.getId(), invoice.getPaymentIntent()), paidAt));
    }

    /**
     * 处理Paypal Subscription 支付成功
     *
     * @param subscribeId
     * @param resource
     */
    @Transactional
    public void handlePaymentSuccessPay(String subscribeId, PayPalWebhookEvent.Resource resource) {
        paymentCallbackHandlers.forEach(paymentCallbackHandler -> paymentCallbackHandler.handlePaymentSuccessPay(subscribeId, resource));
    }

    /**
     * 处理Paypal Subscription 续期成功
     *
     * @param subscriptionId
     * @param resourceId
     * @param payAt
     * @param resource
     */
    @Transactional
    public void handlePayPalPaymentSaleCompleted(String subscriptionId, IInvoice invoice, String payAt, PayPalWebhookEvent.Resource resource) {
        paymentCallbackHandlers.forEach(paymentCallbackHandler -> paymentCallbackHandler.handlePayPalPaymentSaleCompleted(subscriptionId, invoice, payAt, resource));
    }

    /**
     * 处理Paypal Subscription 取消
     *
     * @param subscriptionId
     */
    public void handleBillingSubscriptionCancelled(String subscriptionId) {
        paymentCallbackHandlers.forEach(paymentCallbackHandler -> paymentCallbackHandler.handleBillingSubscriptionCancelled(subscriptionId));
    }

    /**
     * 处理Stripe Customer Subscription Updated Event，主要是将 新的 invoice 和 subscriptionId 绑定
     *
     * @param subscriptionId
     * @param latestInvoiceId
     */
    public void handleStripeCustomSubscriptionUpdatedEvent(String subscriptionId, String latestInvoiceId) {
        paymentCallbackHandlers.forEach(handler -> handler.handleStripeCustomSubscriptionUpdatedEvent(subscriptionId, latestInvoiceId));
    }

    /**
     * 处理Stripe Payment Intent Payment Failed Event
     * 根据 invoice 找到对应的订单，填充 code  declineCode
     *
     * @param invoice
     * @param code
     * @param declineCode
     */
    public void handleStripePaymentIntentPaymentFailedEvent(String invoice, String code, String declineCode) {
        paymentCallbackHandlers.forEach(handler -> handler.handleStripePaymentIntentPaymentFailedEvent(invoice, code, declineCode));
    }


    public void handleStripePaymentIntentCreated(PaymentIntent pi) {
        paymentCallbackHandlers.forEach(h -> h.handleStripePaymentIntentCreated(pi));
    }

    public void handleStripePaymentIntentSucceeded(Invoice ips) {
        paymentCallbackHandlers.forEach(h -> h.handleStripePaymentIntentSucceeded(ips));
    }

    public void handleStripeChargeSucceeded(Charge charge) {
        paymentCallbackHandlers.forEach(h -> h.handleStripeChargeSucceeded(charge));
    }

    /**
     * 处理Stripe Invoice Created Event
     *
     * @param invoice
     */
    public void handleStripeInvoiceCreatedEvent(Invoice invoice) {
        paymentCallbackHandlers.forEach(h -> h.handleStripeInvoiceCreatedEvent(invoice));
    }

    public void handleAppleSubscriptionNotify(String inReq) {
        paymentCallbackHandlers.forEach(h -> h.handleAppleSubscriptionNotify(inReq));
    }
}
