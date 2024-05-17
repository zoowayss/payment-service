package com.coldlake.app.payment.domain.apple;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppleQueryResult extends UnifiedReceipt {
    private Receipt receipt;

    @SerializedName("is-retryable")
    private Boolean is_retryable;

    private App app;

    private Map<String, String> data;

    @Data
    public static class Receipt {

        private Long adam_id;

        private Long app_item_id;

        private String application_version;

        private String bundle_id;

        private Long download_id;

        private String expiration_date;

        private String expiration_date_ms;

        private String expiration_date_pst;

        private String original_application_version;

        private String original_purchase_date;

        private String original_purchase_date_ms;

        private String original_purchase_date_pst;

        private String preorder_date;

        private String preorder_date_ms;

        private String preorder_date_pst;

        private String receipt_creation_date;

        private String receipt_creation_date_ms;

        private String receipt_creation_date_pst;

        private String receipt_type;

        private String request_date;

        private String request_date_ms;

        private String request_date_pst;

        private Long version_external_identifier;

        private List<App> in_app;

    }
}