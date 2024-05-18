package com.coldlake.app.payment.service.payment.apple;

import org.springframework.stereotype.Service;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 14:22
 */
@Service
public class AppleSubscriptionPaymentHandler extends AbstractApplePaymentOrderHandler {
    public static final String APPLE_SUBSCRIBE = "APPLE_SUBSCRIBE";

    @Override
    public String getName() {
        return APPLE_SUBSCRIBE;
    }
}
