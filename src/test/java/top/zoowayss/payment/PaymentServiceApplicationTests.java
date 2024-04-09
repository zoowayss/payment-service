package top.zoowayss.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.zoowayss.payment.service.ThirdPartOrder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SpringBootTest
class PaymentServiceApplicationTests {

    public static final Logger logger = Logger.getLogger(PaymentServiceApplicationTests.class.getName());

    private Map<String, ThirdPartOrder> thirdPartOrderHandler;


    @Autowired
    public void setThirdPartOrderHandler(List<ThirdPartOrder> thirdPartOrders) {
        this.thirdPartOrderHandler = thirdPartOrders.stream().collect(Collectors.toMap(ThirdPartOrder::getName, Function.identity()));
    }

    @Test
    void contextLoads() {


        logger.info("thirdPartOrderHandler: {}" + thirdPartOrderHandler);


    }

}
