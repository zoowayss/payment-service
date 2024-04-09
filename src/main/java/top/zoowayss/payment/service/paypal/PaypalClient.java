package top.zoowayss.payment.service.paypal;

import jakarta.annotation.Resource;
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
import org.springframework.web.client.RestTemplate;
import top.zoowayss.payment.domain.paypal.ApplicationContext;
import top.zoowayss.payment.domain.paypal.orders.ExperienceContext;
import top.zoowayss.payment.domain.paypal.orders.PayPalOrders;
import top.zoowayss.payment.domain.paypal.orders.PaymentSource;
import top.zoowayss.payment.domain.paypal.orders.Paypal;
import top.zoowayss.payment.domain.paypal.subscription.Subscription;
import top.zoowayss.payment.properties.PaypalProperties;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static top.zoowayss.payment.domain.paypal.ApplicationContext.PaymentMethod.IMMEDIATE_PAYMENT_REQUIRED;

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

    <T, R> ResponseEntity<R> requestPost(String uri, T body, Class<R> rClass) {
        return restTemplate.exchange(url + uri, HttpMethod.POST, new HttpEntity<T>(body, obtainAuthedHeaders()), rClass);

    }

    private MultiValueMap<String, String> obtainAuthedHeaders() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        String auth = paypalProperties.getClientId() + ":" + paypalProperties.getSecret();
        headers.add("Authorization", String.format("Basic %s", Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8))));
        headers.add("Prefer", "return=representation");
        headers.add("Content-Type", "application/json");
        return headers;
    }

    public Subscription createSubscription(Subscription subscription) throws Exception {

        ApplicationContext applicationContext = new ApplicationContext(paypalProperties.getBrandName(), paypalProperties.getReturnUrl(), paypalProperties.getCancelUrl(),
                new ApplicationContext.PaymentMethod(IMMEDIATE_PAYMENT_REQUIRED));
        subscription.setApplicationContext(applicationContext);
        ResponseEntity<Subscription> responseEntity = requestPost("/v1/billing/subscriptions", subscription, Subscription.class);
        return responseEntity.getBody();
    }

    public PayPalOrders orderCreate(PayPalOrders payPalOrders) {
        try {
            payPalOrders.setPaymentSource(new PaymentSource(new Paypal(new ExperienceContext(paypalProperties.getBrandName(), paypalProperties.getReturnUrl(), paypalProperties.getCancelUrl()))));
            ResponseEntity<PayPalOrders> responseEntity = requestPost("/v2/checkout/orders", payPalOrders, PayPalOrders.class);
            log.info("create order:{}", responseEntity.getBody());
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Subscription subscriptionDetails(String tradeNo) {
        try {
            ResponseEntity<Subscription> responseEntity = restTemplate.exchange(url + "/v1/billing/subscriptions/" + tradeNo, HttpMethod.GET, new HttpEntity<>(obtainAuthedHeaders()), Subscription.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PayPalOrders retrieveOrder(String tradeNo) {
        try {
            ResponseEntity<PayPalOrders> responseEntity = restTemplate.exchange(url + "/v2/checkout/orders/" + tradeNo, HttpMethod.GET, new HttpEntity<>(obtainAuthedHeaders()), PayPalOrders.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
