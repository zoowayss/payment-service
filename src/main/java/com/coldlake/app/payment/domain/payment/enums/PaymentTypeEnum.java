package com.coldlake.app.payment.domain.payment.enums;

import com.coldlake.app.payment.service.payment.apple.AppleOrderPaymentHandler;
import com.coldlake.app.payment.service.payment.apple.AppleSubscriptionPaymentHandler;
import com.coldlake.app.payment.service.payment.goole.GoogleOrderPaymentHandler;
import com.coldlake.app.payment.service.payment.goole.GoogleSubscribePaymentHandler;
import com.coldlake.app.payment.service.payment.paypal.PaypalOrderHandler;
import com.coldlake.app.payment.service.payment.paypal.PaypalSubscribeOrderHandler;
import com.coldlake.app.payment.service.payment.stripe.StripeOrderHandler;
import com.coldlake.app.payment.service.payment.stripe.StripeSubscriptionHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum PaymentTypeEnum {

    PAYPAL_ORDER(0, PaypalOrderHandler.PAYPAL_ORDER),

    PAYPAL_SUBSCRIBE(1, PaypalSubscribeOrderHandler.PAYPAL_SUBSCRIBE),

    STRIPE_ORDER(2, StripeOrderHandler.STRIPE_ORDER),

    STRIPE_SUBSCRIBE(3, StripeSubscriptionHandler.STRIPE_SUBSCRIPTION),

    APPLE_ORDER(4, AppleOrderPaymentHandler.APPLE_ORDER),

    APPLE_SUBSCRIBE(5, AppleSubscriptionPaymentHandler.APPLE_SUBSCRIBE),

    GOOGLE_ORDER(6, GoogleOrderPaymentHandler.GOOGLE_ORDER),

    GOOGLE_SUBSCRIBE(7, GoogleSubscribePaymentHandler.GOOGLE_SUBSCRIBE);


    private int idx;

    private String desc;


    private static Map<String, PaymentTypeEnum> cache = Arrays.stream(PaymentTypeEnum.values())
            .collect(Collectors.toMap(PaymentTypeEnum::getDesc, Function.identity()));

    public static final Map<Integer, PaymentTypeEnum> idxCache = Arrays.stream(PaymentTypeEnum.values())
            .collect(Collectors.toMap(PaymentTypeEnum::getIdx, Function.identity()));

    public static PaymentTypeEnum of(String desc) {
        return cache.get(desc);
    }

    public static PaymentTypeEnum ofIndex(int idx) {
        return idxCache.get(idx);
    }


}
