package top.zoowayss.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zoowayss.payment.domain.enums.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {


    private String id;

    private OrderStatus status;

    private String paymentUrl;

}




