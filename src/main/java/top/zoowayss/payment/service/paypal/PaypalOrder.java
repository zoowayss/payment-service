package top.zoowayss.payment.service.paypal;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.domain.paypal.Subscription;
import top.zoowayss.payment.service.ThirdPartOrder;

@Service
@Slf4j
public class PaypalOrder implements ThirdPartOrder, InitializingBean {


    @Resource
    private PaypalClient paypalClient;


    @Override
    public Order createSubscription(Product p) {
        return new Order();
    }


    public PaypalOrder createSubscription(Subscription subscription) {

        Subscription createSub = paypalClient.createSubscription(subscription);
        log.info("create subscription:{}", createSub);

        return new PaypalOrder();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Subscription create = new Subscription();
        create.setPlanId("P-6B929488U27333216MYJ5GZI");
        createSubscription(create);
    }
}
