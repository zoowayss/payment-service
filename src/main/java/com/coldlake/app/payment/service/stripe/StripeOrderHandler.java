package com.coldlake.app.payment.service.stripe;

import com.coldlake.app.payment.domain.PaymentOrder;
import com.coldlake.app.payment.domain.PaymentOrderCreation;
import com.coldlake.app.payment.domain.enums.PaymentTypeEnum;
import com.coldlake.app.payment.domain.paypal.orders.enums.ThirdPartOrderStatusAdapterEnum;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return PaymentOrder.newByStripeCheckoutSession(session.getId(), PaymentTypeEnum.STRIPE_ORDER, "", ThirdPartOrderStatusAdapterEnum.of(session.getPaymentStatus()).convert(),
                session.getUrl(), session.getExpiresAt());
    }

    @Override
    public PaymentOrder retrieveOrder(String sessionId) throws Exception {
        String cm = "retrieveOrder@AbstractStripeOrderManager";
        log.info(cm + " sessionId: {} ", sessionId);
        Session session = istripeClient.retrieveOrder(sessionId);

        return PaymentOrder.newByStripeCheckoutSession(session.getId(), PaymentTypeEnum.STRIPE_ORDER, "", ThirdPartOrderStatusAdapterEnum.of(session.getPaymentStatus()).convert(),
                session.getUrl(), session.getExpiresAt());
    }


    @Override
    public String getName() {
        return STRIPE_ORDER;
    }

}
