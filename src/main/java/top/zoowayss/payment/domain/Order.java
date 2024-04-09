package top.zoowayss.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zoowayss.payment.domain.enums.OrderStatus;
import top.zoowayss.payment.domain.paypal.subscription.Link;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {


    private String id;

    private OrderStatus status;

    private String paymentUrl;

    public Order(String id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }

    public Order(String id, OrderStatus status, String key, List<Link> links) {
        this.id = id;
        this.status = status;
        this.paymentUrl = links.stream().filter(l -> key.equals(l.getRel())).findFirst().orElse(Link.EMPTY).getHref();
    }
}




