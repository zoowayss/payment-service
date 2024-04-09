package top.zoowayss.payment.service.paypal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.domain.enums.OrderStatusEnum;
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
        return new Order(createSub.getId(), OrderStatusEnum.of(createSub.getStatus()), "approve", createSub.getLinks());
    }
    @Override
    public Order retriveOrder(String tradeNo) throws Exception {
        Subscription nowSub = paypalClient.subscriptionDetails(tradeNo);

        return new Order(nowSub.getId(), OrderStatusEnum.of(nowSub.getStatus()), "approve", nowSub.getLinks());
    }

    @Override
    public String getName() {
        return "PAYPAL_SUBSCRIBE";
    }
}
