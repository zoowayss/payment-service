package top.zoowayss.payment.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.domain.enums.OrderStatus;
import top.zoowayss.payment.domain.paypal.Subscription;
import top.zoowayss.payment.service.paypal.PaypalClient;

@Service
@Slf4j
public class PaypalSubscribeOrder implements ThirdPartOrder, InitializingBean {

    @Resource
    private PaypalClient paypalClient;


    @Override
    public Order createOrder(Product p) {
        Subscription create = new Subscription();
        create.setPlanId(p.getId());
        create.setStartTime(p.getStartTime());
        Subscription createSub = paypalClient.createSubscription(create);
        log.info("create subscription:{}", createSub);
        return new Order(createSub.getId(), OrderStatus.of(createSub.getStatus()), createSub.getLinks().stream().filter(l -> "approve".equals(l.getRel())).findFirst().orElseThrow().getHref());

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Product p = new Product();
        p.setId("P-6B929488U27333216MYJ5GZI");
        p.setStartTime("2024-04-10T21:30:20.151Z");
        Order order = createOrder(p);
        log.info("order : {}", order);
    }
}
