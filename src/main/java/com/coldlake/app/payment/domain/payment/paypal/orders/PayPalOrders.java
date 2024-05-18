package com.coldlake.app.payment.domain.payment.paypal.orders;

import com.coldlake.app.payment.domain.payment.paypal.orders.enums.ThirdPartOrderStatusAdapterEnum;
import com.coldlake.app.payment.domain.payment.paypal.subscription.Link;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonProperty("id")
    private String id;

    @JsonProperty("intent")
    private String intent;

    @JsonProperty("status")
    private ThirdPartOrderStatusAdapterEnum status;

    @JsonProperty("payment_source")
    private PaymentSource paymentSource;

    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;

    @JsonProperty("payer")
    private Payer payer;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("links")
    private List<Link> links;

    // 构造函数、getter 和 setter 方法省略


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payer {
        @JsonProperty("name")
        private Name name;

        @JsonProperty("email_address")
        private String emailAddress;

        @JsonProperty("payer_id")
        private String payerId;

        @JsonProperty("address")
        private Address address;

        @JsonProperty("status")
        private String status;

        // 构造函数、getter 和 setter 方法省略

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Name {
            @JsonProperty("given_name")
            private String givenName;

            @JsonProperty("surname")
            private String surname;

            // 构造函数、getter 和 setter 方法省略
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Address {
            @JsonProperty("country_code")
            private String countryCode;

            // 构造函数、getter 和 setter 方法省略
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaymentSource {
        @JsonProperty("paypal")
        private PayPal paypal;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PayPal {
        @JsonProperty("email_address")
        private String emailAddress;

        @JsonProperty("account_id")
        private String accountId;

        @JsonProperty("account_status")
        private String accountStatus;

        @JsonProperty("name")
        private Name name;

        @JsonProperty("address")
        private Address address;

        @JsonProperty("experience_context")
        private ExperienceContext experienceContext;

        public PayPal(ExperienceContext experienceContext) {
            this.experienceContext = experienceContext;
        }
        // 构造函数、getter 和 setter 方法省略
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExperienceContext {

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        @JsonProperty("given_name")
        private String givenName;

        @JsonProperty("surname")
        private String surname;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        @JsonProperty("country_code")
        private String countryCode;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PurchaseUnit {
        @JsonProperty("reference_id")
        private String referenceId;

        @JsonProperty("amount")
        private Amount amount;

        @JsonProperty("payee")
        private Payee payee;

        @JsonProperty("soft_descriptor")
        private String softDescriptor;

        @JsonProperty("payments")
        private Payments payments;

        public PurchaseUnit(Amount amount) {
            this.amount = amount;
        }
        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Amount {
        @JsonProperty("currency_code")
        private String currencyCode;

        @JsonProperty("value")
        private String value;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payee {
        @JsonProperty("email_address")
        private String emailAddress;

        @JsonProperty("merchant_id")
        private String merchantId;

        @JsonProperty("display_data")
        private DisplayData displayData;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DisplayData {
        @JsonProperty("brand_name")
        private String brandName;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payments {
        @JsonProperty("captures")
        private List<Capture> captures;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Capture {
        @JsonProperty("id")
        private String id;

        @JsonProperty("status")
        private String status;

        @JsonProperty("amount")
        private Amount amount;

        @JsonProperty("final_capture")
        private boolean finalCapture;

        @JsonProperty("seller_protection")
        private SellerProtection sellerProtection;

        @JsonProperty("seller_receivable_breakdown")
        private SellerReceivableBreakdown sellerReceivableBreakdown;

        @JsonProperty("links")
        private List<Link> links;

        @JsonProperty("create_time")
        private String createTime;

        @JsonProperty("update_time")
        private String updateTime;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SellerProtection {
        @JsonProperty("status")
        private String status;

        @JsonProperty("dispute_categories")
        private List<String> disputeCategories;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SellerReceivableBreakdown {
        @JsonProperty("gross_amount")
        private Amount grossAmount;

        @JsonProperty("paypal_fee")
        private Amount paypalFee;

        @JsonProperty("net_amount")
        private Amount netAmount;

        @JsonProperty("receivable_amount")
        private Amount receivableAmount;

        @JsonProperty("exchange_rate")
        private ExchangeRate exchangeRate;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExchangeRate {
        @JsonProperty("source_currency")
        private String sourceCurrency;

        @JsonProperty("target_currency")
        private String targetCurrency;

        @JsonProperty("value")
        private String value;

        // 构造函数、getter 和 setter 方法省略
    }


}
