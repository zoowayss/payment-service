package top.zoowayss.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String name;

    private String id;

    private String startTime;

    /**
     * 支付货币
     */
    private String currency;

    /**
     * 订单价格
     */
    private String amount;
}
