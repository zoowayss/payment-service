package com.coldlake.app.payment.event.payment;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/18 11:06
 */
public interface WebhookEvent {
    boolean support(String event);
}
