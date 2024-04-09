package top.zoowayss.payment.service.stripe;

import com.stripe.StripeClient;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.properties.StripeProperties;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 14:42
 */
@Component
public class IStripeClient {

    private StripeClient stripeClient;


    private StripeProperties stripeProperties;

    @Autowired
    public IStripeClient(StripeProperties properties) {
        this.stripeProperties = properties; this.stripeClient = new StripeClient(properties.getApiKey());
    }


    public Session createSession(Product p, SessionCreateParams.Mode mode) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(mode)
                    .addLineItem(SessionCreateParams.LineItem.builder().setPrice(p.getId()).setQuantity(1L).build())
//                    .setCancelUrl(stripeProperties.getCancelUrl())
//                    .setUiMode(SessionCreateParams.UiMode.HOSTED)
//                    .setReturnUrl(stripeProperties.getReturnUrl())
                    .setSuccessUrl(stripeProperties.getReturnUrl())
                    .setCancelUrl(stripeProperties.getCancelUrl())
                    .build();

            return stripeClient.checkout().sessions().create(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Session retrieveOrder(String sessionId) {
        try {
            return stripeClient.checkout().sessions().retrieve(sessionId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
