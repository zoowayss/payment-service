package com.coldlake.app.payment.domain.paypal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 14:39
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayPalWebhookValidateResp {

    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILURE = "FAILURE";

    @JsonProperty("verification_status")
    private String verificationStatus;
}
