package top.zoowayss.payment.paypal;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.service.PaypalSubscribeOrder;
import top.zoowayss.payment.service.paypal.PaypalOrder;


/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 09:41
 */
@SpringBootTest
public class PayPalOrderTest {

    final Logger logger = LoggerFactory.getLogger(PayPalOrderTest.class);

    @Resource
    private PaypalSubscribeOrder paypalSubscribeOrder;

    @Test
    public void test() throws Exception {
        Product p = new Product();
        p.setId("P-6B929488U27333216MYJ5GZI");
        p.setStartTime("2024-04-10T21:30:20.151Z");
        Order order = paypalSubscribeOrder.createOrder(p);
        logger.info("order: {}", order);
    }


    @Resource
    private PaypalOrder paypalOrder;

    @Test
    public void testPaypalOrder() throws Exception {
        Product product = new Product();
        product.setCurrency("USD");
        product.setAmount("100.00");
        logger.info("order: {}", paypalOrder.createOrder(product));
    }
}
