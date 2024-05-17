package com.coldlake.app.payment.service.stripe;

import com.coldlake.app.payment.domain.PaymentOrder;
import com.coldlake.app.payment.domain.PaymentOrderCreation;
import com.coldlake.app.payment.domain.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.domain.enums.PaymentTypeEnum;
import com.coldlake.app.payment.domain.paypal.orders.enums.ThirdPartOrderStatusAdapterEnum;
import com.coldlake.app.payment.utils.TimeUtils;
import com.stripe.model.Invoice;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.coldlake.app.payment.service.LogService.cm;

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

        return PaymentOrder.newByStripeCheckoutSession(session.getId(), PaymentTypeEnum.STRIPE_SUBSCRIBE, "", ThirdPartOrderStatusAdapterEnum.of(session.getPaymentStatus()).convert(), session.getUrl(), session.getExpiresAt());
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
    public PaymentOrder retrieveOrder(String subscriptionId) throws Exception {
        if (!StringUtils.hasText(subscriptionId)) {
            throw new RuntimeException(String.format("%s not found subscriptionId. ", cm()));
        }

        Subscription subscription = istripeClient.retrieveSubscription(subscriptionId);

        String latestInvoice = subscription.getLatestInvoice();
        Long paidAt = TimeUtils.getCurrentTimeStamp();
        if (StringUtils.hasText(latestInvoice)) {
            Invoice invoice = istripeClient.retrieveInvoice(latestInvoice);
            paidAt = invoice.getStatusTransitions().getPaidAt();
        }

        return PaymentOrder.newByStripeSubscription(subscriptionId, PaymentTypeEnum.STRIPE_SUBSCRIBE, paidAt, PaymentOrderStatusEnum.ACTIVE, ThirdPartOrderStatusAdapterEnum.of(subscription.getStatus()).convert(), latestInvoice);
    }

    @Override
    public String getName() {
        return "STRIPE_SUBSCRIPTION";
    }

}
