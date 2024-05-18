package com.coldlake.app.payment.domain.payment.paypal.orders;

import com.coldlake.app.payment.domain.payment.paypal.Capture;
import com.coldlake.app.payment.domain.payment.paypal.ShippingAmount;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 09:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseUnit {

    private ShippingAmount amount;

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("payee")
    private Payee payee;

    public PurchaseUnit(ShippingAmount shippingAmount) {
        this.amount = shippingAmount;
    }

    private Payments payments;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payee {
        @JsonProperty("email_address")
        private String emailAddress;

        @JsonProperty("merchant_id")
        private String merchantId;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payments {
        List<Capture> captures;
    }
}
