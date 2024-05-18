package com.coldlake.app.payment.service.payment.listener;

import com.coldlake.app.payment.entity.PaymentChargeOrder;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/19 11:23
 */
public interface PaymentSuccessPayOnlyExecOnceListener {
    void onSuccessPay(PaymentChargeOrder order);
}
