package com.coldlake.app.payment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 14:42
 */
@Data
@Component
@ConfigurationProperties(prefix = "payment.stripe")
public class StripeProperties {

    private String apiKey;

    private String secret;

    private String returnUrl;

    private String cancelUrl;
}
