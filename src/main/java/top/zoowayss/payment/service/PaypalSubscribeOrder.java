package top.zoowayss.payment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.domain.enums.OrderStatus;
import top.zoowayss.payment.domain.paypal.subscription.Subscription;

@Service
@Slf4j
public class PaypalSubscribeOrder extends AbstractPayPalOrder {


    @Override
    public Order createOrder(Product p) throws Exception {
        Subscription create = new Subscription();
        create.setPlanId(p.getId());
        create.setStartTime(p.getStartTime());
        Subscription createSub = paypalClient.createSubscription(create);
        log.info("create subscription:{}", createSub);
        return new Order(createSub.getId(), OrderStatus.of(createSub.getStatus()), createSub.getLinks().stream().filter(l -> "approve".equals(l.getRel())).findFirst().orElseThrow().getHref());

    }
}
