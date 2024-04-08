package top.zoowayss.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {


    private String id;

    private OrderStatus status;


}

@AllArgsConstructor
@Getter
enum OrderStatus {

    CREATE(0, "CREATE"),
    ACTIVE(1, "ACTIVE"),
    CANCELED(2, "CANCELED"),
    COMPLETED(3, "COMPLETED");
    private Integer state;
    private String desc;

}


