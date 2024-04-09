package top.zoowayss.payment.domain.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 09:26
 */
@Data
public class ShippingAmount {

    @JsonProperty("currency_code")
    private String currencyCode;

    private String value;
}
