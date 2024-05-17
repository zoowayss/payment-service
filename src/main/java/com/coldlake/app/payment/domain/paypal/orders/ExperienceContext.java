package com.coldlake.app.payment.domain.paypal.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 10:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceContext {

    public static final String NO_SHIPPING = "NO_SHIPPING";

    public static final String PAY_NOW = "PAY_NOW";

    public static final String IMMEDIATE_PAYMENT_REQUIRED = "IMMEDIATE_PAYMENT_REQUIRED";

    public ExperienceContext(String brandName, String returnUrl, String cancelUrl) {
        this.shippingPreference = NO_SHIPPING;
        this.userAction = PAY_NOW;
        this.paymentMethodPreference = IMMEDIATE_PAYMENT_REQUIRED;
        this.brandName = brandName;
        this.returnUrl = returnUrl;
        this.cancelUrl = cancelUrl;
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


    /**
     * string [ 1 .. 8 ] characters
     * Default: "CONTINUE"
     * Configures a Continue or Pay Now checkout flow.
     * <p>
     * Enum:	Description
     * CONTINUE	After you redirect the customer to the PayPal payment page, a Continue button appears. Use this option when the final amount is not known when the checkout flow is initiated and you want to redirect the
     * customer to the merchant page without processing the payment.
     * PAY_NOW	After you redirect the customer to the PayPal payment page, a Pay Now button appears. Use this option when the final amount is known when the checkout is initiated and you want to process the payment
     * immediately when the customer clicks Pay Now.
     */
    @JsonProperty("user_action")
    private String userAction;


    /**
     * string [ 1 .. 255 ] characters
     * Default: "UNRESTRICTED"
     * The merchant-preferred payment methods.
     * <p>
     * Enum:	Description
     * UNRESTRICTED	Accepts any type of payment from the customer.
     * IMMEDIATE_PAYMENT_REQUIRED	Accepts only immediate payment from the customer. For example, credit card, PayPal balance, or instant ACH. Ensures that at the time of capture, the payment does not have the pending
     * status.
     */
    @JsonProperty("payment_method_preference")
    private String paymentMethodPreference;


    private String locale;

    @JsonProperty("return_url")
    private String returnUrl;


    @JsonProperty("cancel_url")
    private String cancelUrl;
}
