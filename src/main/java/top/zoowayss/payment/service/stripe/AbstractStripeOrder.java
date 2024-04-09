package top.zoowayss.payment.service.stripe;

import com.stripe.model.checkout.Session;
import jakarta.annotation.Resource;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.enums.OrderStatus;
import top.zoowayss.payment.service.ThirdPartOrder;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 14:59
 */

public class AbstractStripeOrder implements ThirdPartOrder {

    @Resource
    protected IStripeClient istripeClient;

    @Override
    public Order retriveOrder(String sessionId) throws Exception {
        Session session = istripeClient.retrieveOrder(sessionId);

        return new Order(session.getId(), OrderStatus.of(session.getPaymentStatus()), session.getUrl());
    }
}
