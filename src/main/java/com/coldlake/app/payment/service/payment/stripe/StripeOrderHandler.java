package com.coldlake.app.payment.service.payment.stripe;

import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.PaymentOrderCreation;
import com.coldlake.app.payment.domain.payment.enums.PaymentTypeEnum;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.coldlake.app.payment.service.payment.LogService.cm;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 15:00
 */
@Service("stripeOrderHandler")
@Slf4j
public class StripeOrderHandler extends AbstractStripeOrderHandler {
    public static final String STRIPE_ORDER = "STRIPE_ORDER";

    @Override
    public PaymentOrder createOrder(PaymentOrderCreation p) throws Exception {
        Session session = istripeClient.createSession(p, SessionCreateParams.Mode.PAYMENT);
        return PaymentOrder.newByStripeCheckoutSession(session.getId(), PaymentTypeEnum.STRIPE_ORDER, "", session.getPaymentStatus(), session.getUrl(), session.getExpiresAt());
    }

    @Override
    public PaymentOrder retrieveOrder(String sessionId) throws Exception {
        String cm = cm();
        log.info(cm + " sessionId: {} ", sessionId);
        Session session = istripeClient.retrieveOrder(sessionId);
        return PaymentOrder.newByStripeCheckoutSession(session.getId(), PaymentTypeEnum.STRIPE_ORDER, "", session.getPaymentStatus(), session.getUrl(), session.getExpiresAt());
    }


    @Override
    public String getName() {
        return STRIPE_ORDER;
    }

}
