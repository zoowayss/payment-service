package top.zoowayss.payment.service.stripe;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.domain.enums.OrderStatus;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 16:15
 */

@Service
@Slf4j
public class StripeSubscription extends AbstractStripeOrder {

    @Override
    public Order createOrder(Product p) throws Exception {
        Session session = istripeClient.createSession(p, SessionCreateParams.Mode.SUBSCRIPTION);
        return new Order(session.getId(), OrderStatus.of(session.getPaymentStatus()), session.getUrl());
    }
}
