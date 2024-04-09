package top.zoowayss.payment.paypal;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.service.PaypalSubscribeOrder;


/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 09:41
 */
@SpringBootTest
@Slf4j
public class PayPalOrderTest {
    @Resource
    private PaypalSubscribeOrder paypalSubscribeOrder;

    @Test
    public void test() {
        Product p = new Product();
        p.setId("P-6B929488U27333216MYJ5GZI");
        p.setStartTime("2024-04-10T21:30:20.151Z");
        Order order = paypalSubscribeOrder.createOrder(p);
        log.info("order: {}", order);
    }
}
