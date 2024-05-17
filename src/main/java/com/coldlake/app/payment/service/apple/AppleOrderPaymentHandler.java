package com.coldlake.app.payment.service.apple;

import org.springframework.stereotype.Component;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 12:15
 */
@Component
public class AppleOrderPaymentHandler extends AbstractApplePaymentOrderHandler {
    public static final String APPLE_ORDER = "APPLE_ORDER";
    @Override
    public String getName() {
        return APPLE_ORDER;
    }
}
