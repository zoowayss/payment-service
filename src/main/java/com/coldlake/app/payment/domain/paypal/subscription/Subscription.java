package com.coldlake.app.payment.domain.paypal.subscription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.coldlake.app.payment.domain.paypal.ApplicationContext;
import com.coldlake.app.payment.domain.paypal.ShippingAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Subscription {

    private String id;

    private String status;

    @JsonProperty("plan_id")
    private String planId;

    @JsonProperty("application_context")
    private ApplicationContext applicationContext;

    private List<Link> links;

    @JsonProperty("shipping_amount")
    private ShippingAmount shippingAmount;

    @JsonProperty("subscriber")
    private Subscriber subscriber;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("billing_info")
    private BillingInfo billingInfo;
    @JsonProperty("start_time")
    private String startTime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Subscriber {
        @JsonProperty("email_address")
        private String emailAddress;
    }
}
