package com.coldlake.app.payment.service.payment.paypal.impl;

import com.coldlake.app.payment.domain.payment.IInvoice;
import com.coldlake.app.payment.domain.payment.paypal.PayPalWebhookEvent;
import com.coldlake.app.payment.domain.payment.paypal.PayPalWebhookValidateResp;
import com.coldlake.app.payment.service.payment.callback.PaymentCallbackHandlerWrapper;
import com.coldlake.app.payment.service.payment.paypal.PaypalClient;
import com.coldlake.app.payment.service.payment.paypal.PaypalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 14:08
 */
@Service
@Slf4j
public class PaypalServiceImpl implements PaypalService {

    public static final String BILLING_SUBSCRIPTION_CANCELLED = "BILLING.SUBSCRIPTION.CANCELLED";

    public static final String CHECKOUT_ORDER_APPROVED = "CHECKOUT.ORDER.APPROVED";

    public static final String PAYMENT_SALE_COMPLETED = "PAYMENT.SALE.COMPLETED";


    @Resource
    private PaymentCallbackHandlerWrapper paymentCallbackHandlerWrapper;

    @Resource
    private PaypalClient paypalClient;

    @Override
    public boolean webhookValidate(Map<String, Object> webhookEvent,
                                   String authLog,
                                   String certUrl,
                                   String transmissionId,
                                   String transmissionSig,
                                   String transmissionTime) {
        String cm = "webhookValidate@PaypalServiceImpl";
        PayPalWebhookValidateResp resp = paypalClient.webhookValidate(webhookEvent, authLog, certUrl, transmissionId, transmissionSig, transmissionTime);
        log.info(cm + " webhook resp:{}", resp);
        return PayPalWebhookValidateResp.STATUS_SUCCESS.equals(resp.getVerificationStatus());

    }

    @Override
    public void dealOrderEvent(PayPalWebhookEvent body) {
        String cm = "dealOrderEvent@PaypalServiceImpl";
        String eventType = body.getEventType();
        if (!StringUtils.hasText(eventType)) {
            log.info(cm + " eventType is empty");
            return;
        }
        PayPalWebhookEvent.Resource resource = Optional.ofNullable(body.getResource())
                .orElseThrow(() -> new IllegalArgumentException(cm + " can not found field: resource "));
        switch (eventType) {
            case BILLING_SUBSCRIPTION_CANCELLED:
                // BILLING_SUBSCRIPTION_CANCELLED 事件
                String subscriptionId = resource.getId();
                if (!StringUtils.hasText(subscriptionId))
                    throw new IllegalArgumentException(cm + " can not found subscriptionId");
                //                paymentCallbackHandlerWrapper.handleBillingSubscriptionCancelled(subscriptionId);
                return;
            case CHECKOUT_ORDER_APPROVED:
                // 一次性支付
                String tradeNo = resource.getId();
                if (!StringUtils.hasText(tradeNo))
                    throw new IllegalArgumentException(cm + " can not found resource id ");
                log.info(cm + " accept CHECKOUT_ORDER_APPROVED_EVENT tradeNo: {}", tradeNo);
                paymentCallbackHandlerWrapper.handlePaymentSuccessPay(tradeNo, resource);
                return;
            case PAYMENT_SALE_COMPLETED:
                // 订阅成功 续期 都会走这里
                subscriptionId = resource.getBillingAgreementId();
                if (!StringUtils.hasText(subscriptionId)) {
                    throw new IllegalArgumentException(cm + " billingAgreementId in resource is empty ");
                }
                String payAt = resource.getCreateTime();
                if (!StringUtils.hasText(payAt))
                    throw new IllegalArgumentException(cm + " can not found resource payAt.");

                paymentCallbackHandlerWrapper.handlePayPalPaymentSaleCompleted(subscriptionId, new IInvoice(), payAt, resource);
                return;
            default:
                log.warn(cm + " we do not care about the webhook event :{}", eventType);

        }

    }
}
