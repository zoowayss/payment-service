package com.coldlake.app.payment.service.goole;

import com.coldlake.app.payment.domain.constants.Constants;
import com.coldlake.app.payment.service.AbstractAppPaymentHandler;

import javax.annotation.Resource;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 15:39
 */

public abstract class AbstractGooglePaymentOrderHandler extends AbstractAppPaymentHandler {

    public static final String ABSTRACT_GOOGLE_PAYMENT_ORDER = "ABSTRACT_GOOGLE_PAYMENT_ORDER";


    @Resource
    protected IGoogleClient googleClient;

    @Override
    public String getSuccessUri() {
        return Constants.GOOGLE_SUCCESS_URI;
    }

    @Override
    public String getFailureUri() {
        return Constants.GOOGLE_FAILED_URI;
    }

    @Override
    public String getName() {
        return ABSTRACT_GOOGLE_PAYMENT_ORDER;
    }
}
