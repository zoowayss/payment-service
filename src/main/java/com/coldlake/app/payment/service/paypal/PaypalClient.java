package com.coldlake.app.payment.service.paypal;

import com.coldlake.app.payment.domain.paypal.ApplicationContext;
import com.coldlake.app.payment.domain.paypal.PayPalWebhookValidateResp;
import com.coldlake.app.payment.domain.paypal.orders.PayPalOrders;
import com.coldlake.app.payment.domain.paypal.orders.PaymentCapture;
import com.coldlake.app.payment.domain.paypal.orders.enums.ThirdPartOrderStatusAdapterEnum;
import com.coldlake.app.payment.domain.paypal.subscription.Subscription;
import com.coldlake.app.payment.properties.PaypalProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableConfigurationProperties(PaypalProperties.class)
@Slf4j
public class PaypalClient {
    public static final String PROD_URL = "https://api-m.sandbox.paypal.com";
    public static final String SANDBOX_URL = "https://api-m.paypal.com";
    public static final String SANDBOX = "sandbox";

    private String url;

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private PaypalProperties paypalProperties;

    @Autowired
    public void setUrl(@Qualifier("paypalProperties") PaypalProperties paypalProperties) {
        this.url = paypalProperties.getMode().equalsIgnoreCase(SANDBOX) ? PROD_URL : SANDBOX_URL;
    }

    <T, R> ResponseEntity<R> requestPost(String uri, T body, Class<R> rClass) throws RestClientException {
        return restTemplate.exchange(url + uri, HttpMethod.POST, new HttpEntity<T>(body, obtainAuthedHeaders()), rClass);

    }

    <T, R> ResponseEntity<R> requestGet(String uri, T body, Class<R> rClass) throws RestClientException {
        return restTemplate.exchange(url + uri, HttpMethod.GET, new HttpEntity<T>(body, obtainAuthedHeaders()), rClass);
    }

    private <T> void challengeResponse(ResponseEntity<T> response) throws Exception {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("response error" + response.getBody());
        }
    }

    private MultiValueMap<String, String> obtainAuthedHeaders() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        String auth = paypalProperties.getClientId() + ":" + paypalProperties.getSecret();
        headers.add("Authorization", String.format("Basic %s", Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8))));
        headers.add("Prefer", "return=representation");
        headers.add("Content-Type", "application/json");
        return headers;
    }

    public Subscription createSubscription(Subscription subscription, String host, String successUrlParams, String cancelUrlParams) throws Exception {
        String cm = "createSubscription@PaypalClient";
        log.info(cm + "subscription:{}", subscription);

        ApplicationContext applicationContext = new ApplicationContext(paypalProperties.getBrandName(),
                (StringUtils.hasText(host) ? host : paypalProperties.getReturnUrl()) + successUrlParams,
                (StringUtils.hasText(host) ? host : paypalProperties.getCancelUrl()) + cancelUrlParams,
                new ApplicationContext.PaymentMethod(ApplicationContext.PaymentMethod.IMMEDIATE_PAYMENT_REQUIRED));
        subscription.setApplicationContext(applicationContext);
        log.info(cm + "param subscription:{}", subscription);
        ResponseEntity<Subscription> responseEntity = requestPost("/v1/billing/subscriptions", subscription, Subscription.class);
        log.info(cm + "responseEntity:{}", responseEntity);
        challengeResponse(responseEntity);
        return responseEntity.getBody();
    }

    public PayPalOrders orderCreate(PayPalOrders payPalOrders, String host, String returnUrlParams, String cancelUrlParams) throws Exception {

        payPalOrders.setPaymentSource(new PayPalOrders.PaymentSource(new PayPalOrders.PayPal(new PayPalOrders.ExperienceContext(paypalProperties.getBrandName(),
                (StringUtils.hasText(host) ? host : paypalProperties.getReturnUrl()) + returnUrlParams,
                (StringUtils.hasText(host) ? host : paypalProperties.getCancelUrl()) + cancelUrlParams))));

        ResponseEntity<PayPalOrders> responseEntity = requestPost("/v2/checkout/orders", payPalOrders, PayPalOrders.class);
        challengeResponse(responseEntity);
        return responseEntity.getBody();
    }

    public Subscription subscriptionDetails(String subscriptionId) throws Exception {
        String cm = "subscriptionDetails@PaypalClient";
        int total = 3;
        int cycle = 0;
        ResponseEntity<Subscription> responseEntity;
        Exception re = null;
        for (int i = 0; i < total; i++) {
            try {
                responseEntity = requestGet("/v1/billing/subscriptions/" + subscriptionId, null, Subscription.class);
                challengeResponse(responseEntity);
                return responseEntity.getBody();
            } catch (Exception e) {
                log.error("{} start retry. times:{} subscriptionId:{}", cm, cycle++, subscriptionId, e);
                Thread.sleep(cycle * 1000L);
                re = e;
            }
        }
        throw re;
    }

    public PayPalOrders retrieveOrder(String tradeNo) throws Exception {
        ResponseEntity<PayPalOrders> responseEntity = requestGet("/v2/checkout/orders/" + tradeNo, null, PayPalOrders.class);
        challengeResponse(responseEntity);
        return responseEntity.getBody();
    }

    public void cancelSubscription(String id, String reason) throws Exception {
        ResponseEntity<String> resp = requestPost("/v1/billing/subscriptions/" + id + "/cancel", Collections.singletonMap("reason", reason), String.class);
        challengeResponse(resp);
    }

    public PayPalWebhookValidateResp webhookValidate(Map<String, Object> webhookEvent, String authLog, String certUrl, String transmissionId, String transmissionSig, String transmissionTime) {
        try {
            String cm = "webhookValidate@PaypalClient";
            log.info(cm + " authLog: {} certUrl: {} transmissionId: {} transmissionSig: {} transmissionTime: {} webhookId: {}  webhookEvent:{} ", authLog, certUrl, transmissionId, transmissionSig,
                    transmissionTime, paypalProperties.getWebhookId(), webhookEvent);
            Map<String, Object> body = new HashMap<>();
            body.put("auth_algo", authLog);
            body.put("cert_url", certUrl);
            body.put("transmission_id", transmissionId);
            body.put("transmission_sig", transmissionSig);
            body.put("transmission_time", transmissionTime);
            body.put("webhook_id", paypalProperties.getWebhookId());
            body.put("webhook_event", webhookEvent);
            ResponseEntity<PayPalWebhookValidateResp> response = requestPost("/v1/notifications/verify-webhook-signature", body, PayPalWebhookValidateResp.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Subscription agreementExecute(String tradeNo) throws Exception {
        String cm = "agreementExecute@PaypalClient";
        ResponseEntity<Subscription> responseEntity = requestPost(String.format("/v1/payments/billing-agreements/%s/agreement-execute", tradeNo), null, Subscription.class);
        challengeResponse(responseEntity);
        log.info("{} agreementExecute({}) resp: {}", cm, tradeNo, responseEntity);
        return responseEntity.getBody();
    }

    public PaymentCapture captureOrder(String id) {
        String cm = String.format("captureOrder@PaypalClient@%s", Thread.currentThread().getName());
        try {
            ResponseEntity<PaymentCapture> responseEntity = requestPost(String.format("/v2/checkout/orders/%s/capture", id), Collections.emptyMap(), PaymentCapture.class);
            log.info("{} id: {} resp: {}", cm, id, responseEntity);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("{} params id: {} error", cm, id, e);
            return new PaymentCapture("", ThirdPartOrderStatusAdapterEnum.APPROVED);
        }

    }
}
