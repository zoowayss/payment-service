package com.coldlake.app.payment.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 17:09
 */
@Getter
public class AppleSuccessPayEvent<T> extends ApplicationEvent {
    private T data;

    public AppleSuccessPayEvent(T data) {
        super(data);
        this.data = data;
    }
}
