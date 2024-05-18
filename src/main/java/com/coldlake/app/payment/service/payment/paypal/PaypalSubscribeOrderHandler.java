package com.coldlake.app.payment.service.payment.paypal;

import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.PaymentOrderCreation;
import com.coldlake.app.payment.domain.payment.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.domain.payment.enums.PaymentTypeEnum;
import com.coldlake.app.payment.domain.payment.paypal.orders.enums.ThirdPartOrderStatusAdapterEnum;
import com.coldlake.app.payment.domain.payment.paypal.subscription.BillingInfo;
import com.coldlake.app.payment.domain.payment.paypal.subscription.LastPayment;
import com.coldlake.app.payment.domain.payment.paypal.subscription.Subscription;
import com.coldlake.app.payment.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Slf4j
public class PaypalSubscribeOrderHandler extends AbstractPayPalOrderHandler {

    public static final String PAYPAL_SUBSCRIBE = "PAYPAL_SUBSCRIBE";

    @Override
    public PaymentOrder createOrder(PaymentOrderCreation p) throws Exception {
        String cm = "createOrder@PaypalSubscribeOrder";
        log.info(cm + "product:{}", p);

        Subscription create = new Subscription();
        create.setPlanId(p.getSkuId());
        create.setStartTime(getPlanStartTime(p.getCycle()));
        Subscription createSub = paypalClient.createSubscription(create, p.getHost(), p.getSuccessUrlParams(), p.getCancelUrlParams());
        log.info(cm + "create subscription:{}", createSub);
        PaymentOrder ans = new PaymentOrder(createSub.getId(), PaymentTypeEnum.PAYPAL_SUBSCRIBE, createSub.getId(), ThirdPartOrderStatusAdapterEnum
                .of(createSub.getStatus()).convert(), "approve", createSub.getLinks());
        log.info(cm + " ans:{}", ans);
        return ans;
    }

    @Override
    public PaymentOrder retrieveOrder(String tradeNo) throws Exception {
        String cm = "retriveOrder@PaypalSubscribeOrder";
        log.info(cm + "tradeNo:{}", tradeNo);
        Subscription nowSub = paypalClient.subscriptionDetails(tradeNo);
        log.info(cm + " retrieve order result:{}", nowSub);
        LastPayment lastPayment = Optional.ofNullable(Optional.ofNullable(nowSub.getBillingInfo()).orElse(new BillingInfo()).getLastPayment())
                .orElse(new LastPayment());
        long lastPaidTime = StringUtils.hasText(lastPayment.getTime()) ? TimeUtils.timeFormatTZ(lastPayment.getTime()) : TimeUtils.getCurrentTimeMils();
        PaymentOrderStatusEnum status = ThirdPartOrderStatusAdapterEnum.of(nowSub.getStatus()).convert();
        return new PaymentOrder(nowSub.getId(), PaymentTypeEnum.PAYPAL_SUBSCRIBE, nowSub.getId(), lastPaidTime, status, status, "approve", nowSub.getLinks());
    }

    @Override
    public void cancelSubscription(String subscribeId, String reason) throws Exception {
        String cm = "cancelOrder@PaypalSubscribeOrder";
        if (!StringUtils.hasText(subscribeId)) {
            log.warn(cm + " subscribeId is empty. ");
            throw new RuntimeException("subscribeId is empty");
        }
        log.info(cm + " cancel subscription subscribeId: {} reason: {} ", subscribeId, reason);
        paypalClient.cancelSubscription(subscribeId, reason);
    }

    @Override
    public String getName() {
        return PAYPAL_SUBSCRIBE;
    }

}
