package top.zoowayss.payment.domain.paypal.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zoowayss.payment.domain.paypal.ShippingAmount;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 09:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseUnits {

    private ShippingAmount amount;
}
