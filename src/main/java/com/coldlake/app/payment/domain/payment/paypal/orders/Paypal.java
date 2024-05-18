package com.coldlake.app.payment.domain.payment.paypal.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 10:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Paypal {

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("account_status")
    private String accountStatus;

    @JsonProperty("experience_context")
    private ExperienceContext experienceContext;

    public Paypal(ExperienceContext experienceContext) {
        this.experienceContext = experienceContext;
    }
}
