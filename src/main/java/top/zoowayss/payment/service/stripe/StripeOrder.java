package top.zoowayss.payment.service.stripe;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.domain.enums.OrderStatusEnum;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 15:00
 */
@Service
@Slf4j
public class StripeOrder extends AbstractStripeOrder {
    @Override
    public Order createOrder(Product p) throws Exception {

        Session session = istripeClient.createSession(p, SessionCreateParams.Mode.PAYMENT);
        log.info("session: {},sessionId:{}", session, session.getId());
        return new Order(session.getId(), OrderStatusEnum.of(session.getPaymentStatus()), session.getUrl());
    }

    @Override
    public String getName() {
        return "STRIPE_ORDER";
    }
}
