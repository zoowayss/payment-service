package com.coldlake.app.payment.domain.paypal;

import com.coldlake.app.payment.domain.paypal.subscription.BillingInfo;
import com.coldlake.app.payment.domain.paypal.subscription.Link;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.coldlake.app.payment.domain.paypal.orders.PaymentSource;
import com.coldlake.app.payment.domain.paypal.orders.PurchaseUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayPalWebhookEvent {

    private String id;

    @JsonProperty("event_version")
    private String eventVersion;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("resource_version")
    private String resourceVersion;

    @JsonProperty("event_type")
    private String eventType;
    private String summary;
    private Resource resource;
    private List<Link> links;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Resource {
        private String id;
        @JsonProperty("create_time")
        private String createTime;
        @JsonProperty("update_time")
        private String updateTime;
        private String state;
        private Amount amount;
        @JsonProperty("parent_payment")
        private String parentPayment;
        @JsonProperty("valid_until")
        private String validUntil;

        @JsonProperty("billing_agreement_id")
        private String billingAgreementId;
        private String description;
        private List<Link> links;

        private String intent;

        private String status;

        private Payer payer;

        @JsonProperty("payment_source")
        private PaymentSource paymentSource;
        @JsonProperty("purchase_units")
        private List<PurchaseUnit> purchaseUnits;

        @JsonProperty("billing_info")
        private BillingInfo billingInfo;

        // Getters and Setters
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Amount {
        private String total;
        private String currency;
        private Details details;

        // Getters and Setters
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Details {
        private String subtotal;
    }
}