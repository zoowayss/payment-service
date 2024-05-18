package com.coldlake.app.payment.service.payment.stripe;

import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.PaymentOrderCreation;
import com.coldlake.app.payment.domain.payment.enums.PaymentTypeEnum;
import com.coldlake.app.payment.utils.TimeUtils;
import com.stripe.model.Invoice;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.coldlake.app.payment.service.payment.LogService.cm;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 16:15
 */

@Service("stripeSubscriptionHandler")
@Slf4j
public class StripeSubscriptionHandler extends AbstractStripeOrderHandler {

    public static final String STRIPE_SUBSCRIPTION = "STRIPE_SUBSCRIPTION";

    @Override
    public PaymentOrder createOrder(PaymentOrderCreation p) throws Exception {
        String cm = "createOrder@StripeSubscriptionHandler";

        Session session = istripeClient.createSubscriptionSession(p);
        // 这里 subscribeId 获取不到，只能依赖 stripe 的 webhook 来获取
        log.info("{} session: {}", cm, session);

        return PaymentOrder.newByStripeCheckoutSession(session.getId(), PaymentTypeEnum.STRIPE_SUBSCRIBE, "", session.getPaymentStatus(), session.getUrl(), session.getExpiresAt());
    }

    @Override
    public void cancelSubscription(String subscribeId, String reason) throws Exception {
        String cm = "cancelOrder@StripeSubscriptionManager";
        if (!StringUtils.hasText(subscribeId)) {
            log.warn(cm + " subscribeId is empty. ");
            throw new RuntimeException("subscribeId is empty");
        }
        log.info(cm + " subscribeId: {} reason: {} ", subscribeId, reason);
        istripeClient.cancelOrder(subscribeId);
    }

    @Override
    public PaymentOrder retrieveOrder(String tradeNo) throws Exception {
        if (!StringUtils.hasText(tradeNo)) {
            throw new RuntimeException(String.format("%s not found tradeNo. ", cm()));
        }
        Session session = istripeClient.retrieveOrder(tradeNo);
        return PaymentOrder.newByStripeCheckoutSession(tradeNo, PaymentTypeEnum.STRIPE_SUBSCRIBE, "", session.getPaymentStatus(), session.getUrl(), session.getExpiresAt());
    }

    @Override
    public PaymentOrder retrieveSubscription(String subId) throws Exception {
        if (!StringUtils.hasText(subId)) {
            throw new RuntimeException(String.format("%s not found subscriptionId. ", cm()));
        }

        Subscription subscription = istripeClient.retrieveSubscription(subId);

        String latestInvoice = subscription.getLatestInvoice();
        Long paidAt = TimeUtils.getCurrentTimeStamp();
        if (StringUtils.hasText(latestInvoice)) {
            Invoice invoice = istripeClient.retrieveInvoice(latestInvoice);
            paidAt = invoice.getStatusTransitions()
                    .getPaidAt();
        }

        return PaymentOrder.newByStripeSubscription(subId, PaymentTypeEnum.STRIPE_SUBSCRIBE, paidAt, subscription.getStatus(), subscription.getStatus(), latestInvoice);
    }

    @Override
    public String getName() {
        return "STRIPE_SUBSCRIPTION";
    }

}
