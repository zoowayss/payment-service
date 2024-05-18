package com.coldlake.app.payment.service.payment.apple;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/15 16:18
 */
public interface AppleService {
    void handleAppleSubscriptionNotify(String inReq);
}
