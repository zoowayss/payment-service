package top.zoowayss.payment.service.paypal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import top.zoowayss.payment.domain.paypal.Subscription;
import top.zoowayss.payment.properties.PaypalProperties;

@Component
@EnableConfigurationProperties(PaypalProperties.class)
@Slf4j
public class PaypalClient {
    public static final String PROD_URL = "https://api-m.sandbox.paypal.com";
    public static final String SANDBOX_URL = "https://api-m.paypal.com";
    public static final String SANDBOX = "sandbox";

    private String url;

//    @Resource
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public void setUrl(PaypalProperties paypalProperties) {
        this.url = paypalProperties.getMode().equalsIgnoreCase(SANDBOX) ? PROD_URL : SANDBOX_URL;
    }

    <T, R> ResponseEntity<R> requestPost(String uri, T body, Class<R> rClass) {
        MultiValueMap<String,String> headers =new HttpHeaders();
        headers.add("Authorization","Bearer A21AAJHmlnckjd7nB0vhWmfxDztjzLk0giaLKzhQFr2qMCveOqhzhyZtBKoFieXPzdK8h1vpJ901vCMzqPJFrGDaUapBZ9y5Q");
        headers.add("Content-Type","application/json");
        return restTemplate.exchange(url + uri, HttpMethod.POST, new HttpEntity<T>(body,headers), rClass);

    }

    public Subscription createSubscription(Subscription subscription) {

        try {
            ResponseEntity<Subscription> responseEntity = requestPost("/v1/billing/subscriptions", subscription, Subscription.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
