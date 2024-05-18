package com.coldlake.app.payment.domain.payment.paypal;

import com.coldlake.app.payment.domain.payment.paypal.subscription.Link;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Capture {
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

        // 构造函数、getter 和 setter 方法省略
    }
}
