package com.coldlake.app.payment.service.stripe;

import com.coldlake.app.payment.domain.PaymentOrderCreation;
import com.coldlake.app.payment.domain.constants.Constants;
import com.coldlake.app.payment.properties.StripeProperties;
import com.coldlake.app.payment.utils.JsonUtils;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Invoice;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.coldlake.app.payment.domain.constants.Constants.ONE_SECOND_MILL;
import static com.coldlake.app.payment.service.LogService.cm;
import static com.coldlake.app.payment.service.PaymentOrderHandler.METADATA_FIELD_ORDER_ID;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 14:42
 */
@Component
@Slf4j
public class IStripeClient {

    private StripeClient stripeClient;


    private StripeProperties stripeProperties;

    @Autowired
    public IStripeClient(StripeProperties properties) {
        this.stripeProperties = properties;
        this.stripeClient = new StripeClient(properties.getApiKey());
    }


    public Session createSession(PaymentOrderCreation p, SessionCreateParams.Mode mode) throws StripeException {
        log.debug("{} product: {} , mode: {}", cm(), JsonUtils.logJson(p), mode);
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(mode)
                .putMetadata(METADATA_FIELD_ORDER_ID, p.getOrderId())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice(p.getId()).setQuantity(p.getQuantity()).build())
                .setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder().putMetadata(METADATA_FIELD_ORDER_ID, p.getOrderId()).build())
                .setSuccessUrl((StringUtils.hasText(p.getHost()) ? p.getHost() : stripeProperties.getReturnUrl()) + p.getSuccessUrlParams())
                .setCancelUrl((StringUtils.hasText(p.getHost()) ? p.getHost() : stripeProperties.getCancelUrl()) + p.getCancelUrlParams())
                .setExpiresAt(p.getExpiresAt() / ONE_SECOND_MILL)
                .build();
        Session res = stripeClient.checkout().sessions().create(params);
        log.debug("{} checkout session succeed. res: {}", cm(), ApiResource.GSON.toJson(res));
        return res;
    }


    public Session retrieveOrder(String sessionId) throws StripeException {
        log.debug("{} param sessionId: {}", cm(), sessionId);
        Session res = stripeClient.checkout().sessions().retrieve(sessionId);
        log.debug("{} res data: {}", cm(), ApiResource.GSON.toJson(res));
        return res;
    }

    public void cancelOrder(String subscribeId) throws StripeException {
        stripeClient.subscriptions().cancel(subscribeId);
    }

    public Subscription retrieveSubscription(String subscribeId) throws StripeException {
        return stripeClient.subscriptions().retrieve(subscribeId);
    }

    public Invoice retrieveInvoice(String invoice) throws StripeException {
        return stripeClient.invoices().retrieve(invoice);
    }

    public Session createSubscriptionSession(PaymentOrderCreation p) throws StripeException {
        log.debug("{} params: {}", cm(), JsonUtils.logJson(p));
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .putMetadata(METADATA_FIELD_ORDER_ID, p.getOrderId())
                .setSubscriptionData(SessionCreateParams.SubscriptionData.builder().putMetadata(METADATA_FIELD_ORDER_ID, p.getOrderId()).build())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice(p.getId()).setQuantity(p.getQuantity()).build())
                .setSuccessUrl((StringUtils.hasText(p.getHost()) ? p.getHost() : stripeProperties.getReturnUrl()) + p.getSuccessUrlParams())
                .setCancelUrl((StringUtils.hasText(p.getHost()) ? p.getHost() : stripeProperties.getCancelUrl()) + p.getCancelUrlParams())
                .setExpiresAt(p.getExpiresAt() / ONE_SECOND_MILL)
                .build();
        Session res = stripeClient.checkout().sessions().create(params);
        log.debug("{} res: {} ", cm(), ApiResource.GSON.toJson(res));
        return res;
    }
}
