package com.coldlake.app.payment.service.stripe;


import com.coldlake.app.payment.domain.PaymentOrder;
import com.coldlake.app.payment.domain.constants.Constants;
import com.coldlake.app.payment.domain.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.service.AbstractPaymentHandler;
import com.stripe.model.checkout.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 14:59
 */
@Slf4j
public abstract class AbstractStripeOrderHandler extends AbstractPaymentHandler {

    public static final String ABSTRACT_STRIPE_ORDER = "ABSTRACT_STRIPE_PAYMENT_ORDER";
    @Resource
    protected IStripeClient istripeClient;


    @Override
    public String getName() {
        return ABSTRACT_STRIPE_ORDER;
    }

    @Override
    public String getSuccessUri() {
        return Constants.STRIPE_SUCCESS_URI;
    }

    @Override
    public String getFailureUri() {
        return Constants.STRIPE_FAILED_URI;
    }
}
