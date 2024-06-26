package com.coldlake.app.payment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment.paypal")
@Data
public class PaypalProperties {

    private String mode;

    private String clientId;

    private String secret;

    private String returnUrl;

    private String cancelUrl;

    private String brandName;

    private String webhookId;
}
