package com.coldlake.app.payment.service.payment.paypal;

import com.coldlake.app.payment.domain.payment.paypal.PayPalWebhookEvent;

import java.util.Map;

/**
 * @Description:
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 14:08
 */

public interface PaypalService {
    boolean webhookValidate(Map<String, Object> body,
                            String authLog,
                            String certUrl,
                            String transmissionId,
                            String transmissionSig,
                            String transmissionTime);

    void dealOrderEvent(PayPalWebhookEvent body) throws Exception;
}
