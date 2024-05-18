package com.coldlake.app.payment.domain.payment.apple;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LatestReceiptInfo extends App {
    private String app_account_token;

    private String in_app_ownership_type;

    private String is_upgraded;

    private String offer_code_ref_name;

    private String subscription_group_identifier;
}
