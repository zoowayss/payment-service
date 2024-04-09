package top.zoowayss.payment.domain.paypal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationContext {

    public static final String NO_SHIPPING = "NO_SHIPPING";

    public ApplicationContext(String brandName, String returnUrl, String cancelUrl, PaymentMethod paymentMethod) {
        this.brandName = brandName;
        this.shippingPreference = NO_SHIPPING;
        this.returnUrl = returnUrl;
        this.cancelUrl = cancelUrl;
        this.paymentMethod = paymentMethod;
    }

    @JsonProperty("brand_name")
    private String brandName;


    /**
     * string [ 1 .. 24 ] characters
     * Default: "GET_FROM_FILE"
     * The location from which the shipping address is derived.
     * <p>
     * Enum:	Description
     * GET_FROM_FILE	Get the customer-provided shipping address on the PayPal site.
     * NO_SHIPPING	Redacts the shipping address from the PayPal site. Recommended for digital goods.
     * SET_PROVIDED_ADDRESS	Get the merchant-provided address. The customer cannot change this address on the PayPal site. If merchant does not pass an address, customer can choose the address on PayPal pages.
     */
    @JsonProperty("shipping_preference")
    private String shippingPreference;

    @JsonProperty("user_action")
    private String userAction;

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("cancel_url")
    private String cancelUrl;


    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;


    @Data
    public static class PaymentMethod {
        public static final String IMMEDIATE_PAYMENT_REQUIRED = "IMMEDIATE_PAYMENT_REQUIRED";
        @JsonProperty("payer_selected")
        private String payerSelected;

        @JsonProperty("payee_preferred")
        private String payeePreferred;

        public PaymentMethod(String payeePreferred) {
            this.payeePreferred = payeePreferred;
        }
    }
}
