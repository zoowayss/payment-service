package com.coldlake.app.payment.service.stripe.impl;

import com.coldlake.app.payment.service.handler.callback.PaymentCallbackHandlerWrapper;
import com.coldlake.app.payment.service.stripe.StripeService;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 09:04
 */
@Service
@Slf4j
public class StripeServiceImpl implements StripeService {

    /**
     * 一次性支付成功 会走这个事件
     */
    public static final String CHECKOUT_SESSION_COMPLETED_EVENT = "checkout.session.completed";

    /**
     * 订阅 支付成功 会有这个事件
     */
    public static final String INVOICE_PAID_EVENT = "invoice.paid";

    /**
     * 一次性支付 订阅 都会产生 PAYMENT_INTENT_CREATED
     */
    public static final String PAYMENT_INTENT_CREATED = "payment_intent.created";
    /**
     * 订阅类型支付成功 更新payment intent
     */
    public static final String INVOICE_PAYMENT_SUCCEEDED = "invoice.payment_succeeded";

    /**
     * 一次性支付成功 对账
     */
    public static final String CHARGE_SUCCEEDED = "charge.succeeded";
    /**
     * 客户订阅更新，会携带 latest_invoice 字段 ，可以通过 latest_invoice 获取到最新的 invoice
     * 需要更新 invoice ， 这样 当 payment.intent.failed 事件触发时，需要根据 invoice 能找到是哪个订单
     */
    public static final String CUSTOMER_SUBSCRIPTION_UPDATED = "customer.subscription.updated";

    /**
     * 付款失败，把失败原因记录下来
     */
    public static final String PAYMENT_INTENT_PAYMENT_FAILED = "payment_intent.payment_failed";


    /**
     * 订阅才会有 账单创建事件
     */
    public static final String INVOICE_CREATED = "invoice.created";

    public static final String INVOICE_UPDATED = "invoice.updated";
    @Resource
    private PaymentCallbackHandlerWrapper paymentCallbackHandlerWrapper;


    @Override
    public Integer handleWebhook(String payload, String sigHeader, HttpServletRequest request) {
        String cm = "handleWebhook@StripeServiceImpl";

        Event event = ApiResource.GSON.fromJson(payload, Event.class);
        switch (event.getType()) {
            case CHECKOUT_SESSION_COMPLETED_EVENT: {
                Session session = ApiResource.GSON.fromJson(event.getDataObjectDeserializer().getRawJson(), Session.class);
                String orderId =
                        Optional.ofNullable(Optional.ofNullable(session.getMetadata()).orElseThrow(() -> new IllegalArgumentException("metadata is null")).get("orderId")).orElseThrow(() -> new IllegalArgumentException("orderId is null"));

                String mode = session.getMode();
                paymentCallbackHandlerWrapper.handleStripeCheckoutSessionCompletedEvent(orderId, mode);
                break;
            }

            case INVOICE_PAID_EVENT: {

                Invoice invoice = ApiResource.GSON.fromJson(event.getDataObjectDeserializer().getRawJson(), Invoice.class);

                String subscriptionId = invoice.getSubscription();
                if (!StringUtils.hasText(subscriptionId)) throw new IllegalArgumentException("subscriptionId is empty");

                String invoiceId = invoice.getId();
                if (!StringUtils.hasText(invoiceId)) {
                    throw new IllegalArgumentException("invoiceId is empty");
                }

                Long paidAt = Optional.ofNullable(invoice.getStatusTransitions()).orElseThrow(() -> new IllegalArgumentException("invoice.statusTransitions is null")).getPaidAt();
                if (paidAt == null || paidAt == 0L)
                    throw new IllegalArgumentException("invoice.statusTransitions.paidAt is null");
                paymentCallbackHandlerWrapper.handleStripeInvoicePaidEvent(subscriptionId, invoice, paidAt);
                break;
            }

            case CUSTOMER_SUBSCRIPTION_UPDATED:
                Subscription subscription = ApiResource.GSON.fromJson(event.getDataObjectDeserializer().getRawJson(), Subscription.class);
                if (subscription == null)
                    throw new IllegalArgumentException(String.format("%s handle CUSTOMER_SUBSCRIPTION_UPDATED event, subscription is null", cm));

                String subscriptionId = subscription.getId();
                if (!StringUtils.hasText(subscriptionId))
                    throw new IllegalArgumentException(String.format("%s handle CUSTOMER_SUBSCRIPTION_UPDATED event, subscriptionId is empty", cm));

                String latestInvoiceId = subscription.getLatestInvoice();
                if (!StringUtils.hasText(latestInvoiceId))
                    throw new IllegalArgumentException(String.format("%s handle CUSTOMER_SUBSCRIPTION_UPDATED event, latestInvoiceId is empty", cm));

                paymentCallbackHandlerWrapper.handleStripeCustomSubscriptionUpdatedEvent(subscriptionId, latestInvoiceId);
                break;

            case PAYMENT_INTENT_PAYMENT_FAILED:
                PaymentIntent paymentIntent = ApiResource.GSON.fromJson(event.getDataObjectDeserializer().getRawJson(), PaymentIntent.class);
                if (paymentIntent == null)
                    throw new IllegalArgumentException(String.format("%s handle PAYMENT_INTENT_PAYMENT_FAILED event, paymentIntent is null", cm));

                StripeError paymentError = Optional.ofNullable(paymentIntent.getLastPaymentError()).
                        orElseThrow(() -> new IllegalArgumentException(String.format("%s handle PAYMENT_INTENT_PAYMENT_FAILED event, paymentError is null", cm)));

                String code = paymentError.getCode();
                String declineCode = paymentError.getDeclineCode();

                paymentCallbackHandlerWrapper.handleStripePaymentIntentPaymentFailedEvent(paymentIntent.getId(), code, declineCode);
                break;

            case INVOICE_CREATED:

            case INVOICE_UPDATED:
                Invoice invoice = ApiResource.GSON.fromJson(event.getDataObjectDeserializer().getRawJson(), Invoice.class);
                paymentCallbackHandlerWrapper.handleStripeInvoiceCreatedEvent(invoice);
                break;

            case PAYMENT_INTENT_CREATED:
                PaymentIntent pi = ApiResource.GSON.fromJson(event.getDataObjectDeserializer().getRawJson(), PaymentIntent.class);
                paymentCallbackHandlerWrapper.handleStripePaymentIntentCreated(pi);
                break;

            case INVOICE_PAYMENT_SUCCEEDED:
                Invoice ips = ApiResource.GSON.fromJson(event.getDataObjectDeserializer().getRawJson(), Invoice.class);
                paymentCallbackHandlerWrapper.handleStripePaymentIntentSucceeded(ips);
                break;


            case CHARGE_SUCCEEDED:
                Charge charge = ApiResource.GSON.fromJson(event.getDataObjectDeserializer().getRawJson(), Charge.class);
                paymentCallbackHandlerWrapper.handleStripeChargeSucceeded(charge);
                break;
            default:
                log.warn(cm + "Unhandled event type: {}", event.getType());
        }

        return HttpStatus.OK.value();
    }
}
