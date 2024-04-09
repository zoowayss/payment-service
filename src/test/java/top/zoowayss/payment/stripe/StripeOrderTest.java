package top.zoowayss.payment.stripe;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.service.stripe.StripeOrder;
import top.zoowayss.payment.service.stripe.StripeSubscription;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 15:06
 */
@SpringBootTest
public class StripeOrderTest {

    public static final Logger log = LoggerFactory.getLogger(StripeOrderTest.class);

    @Resource
    StripeOrder stripeOrder;


    @Test
    public void testCreateOrder() throws Exception {

        Product p = new Product("test_sku", "price_1P3ZZBJext5rS05X5srSel6d", "", "","");
        Order order = stripeOrder.createOrder(p);

        log.info("order: {}", order);
    }


    @Test
    public void testRetriveOrder() throws Exception {
        Order order = stripeOrder.retriveOrder("cs_test_a1YxnePE17LP1rgIuVchhuMnXHl3nE27g4g3YnkSerhJSDp7DzcC6v4tuY");
        log.info("order: {}", order);
    }


    @Resource
    StripeSubscription stripeSubscription;

    @Test
    public void createStripeSubscription() throws Exception {
        Product product = new Product();
        product.setId("price_1P3ZEbJext5rS05XkwbXGo1I");
        Order order = stripeSubscription.createOrder(product);
        log.info("order: {}", order);
    }

    @Test
    public void retriveStripeSubscription() throws Exception {
        Order order = stripeSubscription.retriveOrder("cs_test_a1mHrHFpNyXFeZ49FbsabMqM6PcKTpVJk6Xn5zCLZ0g5KCVtOc3hjLPhcr");
        log.info("order: {}", order);
    }
}
