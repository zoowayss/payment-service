package top.zoowayss.payment.domain.paypal.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zoowayss.payment.domain.paypal.subscription.Link;

import java.util.List;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 09:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayPalOrders {
    private String id;

    /**
     * string
     * The intent to either capture payment immediately or authorize a payment for an order after order creation.
     * <p>
     * Enum:	Description
     * CAPTURE	The merchant intends to capture payment immediately after the customer makes a payment.
     * AUTHORIZE	The merchant intends to authorize a payment and place funds on hold after the customer makes a payment. Authorized payments are best captured within three days of authorization but are available to
     * capture for up to 29 days. After the three-day honor period, the original authorized payment expires and you must re-authorize the payment. You must make a separate request to capture payments on demand. This
     * intent is not supported when you have more than one purchase_unit within your order.
     */
    private String intent;

    private String status;


    private List<Link> links;
    @JsonProperty("purchase_units")
    private List<PurchaseUnits> purchaseUnits;





    @JsonProperty("payment_source")
    private PaymentSource paymentSource;

}
