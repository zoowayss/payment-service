package com.coldlake.app.payment.service.stripe;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 09:04
 */

public interface StripeService {
    Integer handleWebhook(String payload, String sigHeader, HttpServletRequest request);
}
