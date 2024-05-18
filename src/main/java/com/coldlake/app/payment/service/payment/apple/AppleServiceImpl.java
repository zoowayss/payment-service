package com.coldlake.app.payment.service.payment.apple;

import com.coldlake.app.payment.service.payment.callback.PaymentCallbackHandlerWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/15 16:18
 */
@Service
@Slf4j
public class AppleServiceImpl implements AppleService {

    @Resource
    private PaymentCallbackHandlerWrapper paymentCallbackHandlerWrapper;

    @Override
    public void handleAppleSubscriptionNotify(String inReq) {
        paymentCallbackHandlerWrapper.handleAppleSubscriptionNotify(inReq);

    }
}
