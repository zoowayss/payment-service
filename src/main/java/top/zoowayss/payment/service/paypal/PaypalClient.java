package top.zoowayss.payment.service.paypal;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import top.zoowayss.payment.domain.paypal.Subscription;
import top.zoowayss.payment.properties.PaypalProperties;

import java.io.IOException;

@Component
@EnableConfigurationProperties(PaypalProperties.class)
@Slf4j
public class PaypalClient {
    public static final String PROD_URL = "https://api-m.sandbox.paypal.com";
    public static final String SANDBOX_URL = "https://api-m.paypal.com";
    public static final String SANDBOX = "sandbox";

    private String url;

    @Resource
    private PaypalProperties paypalProperties;

    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    public void setUrl(@Qualifier("paypalProperties") PaypalProperties PaypalProperties) {
        this.url = paypalProperties.getMode().equalsIgnoreCase(SANDBOX) ? PROD_URL : SANDBOX_URL;
    }

    Request requestPost(String uri) {
        return Request.post(url + uri).addHeader("Authorization", "Bearer A21AAKmOrq5hMxr8CjMSwkMdQcKpdsH7HdHNelgM_9z4x72PvwT4_A08w0OTxFwPfwPtclBA4NwcCOkuvFp0NoqsF8z6O_t2w").addHeader("Content-Type", "application/json");

    }

    public Subscription createSubscription(Subscription subscription) {

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(subscription);
            Response response = requestPost("/v1/billing/subscriptions").bodyByteArray(bytes).execute();
            return objectMapper.readValue(response.returnContent().asBytes(), Subscription.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
