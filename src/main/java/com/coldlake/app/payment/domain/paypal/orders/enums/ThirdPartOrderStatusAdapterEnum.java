package com.coldlake.app.payment.domain.paypal.orders.enums;

import com.coldlake.app.payment.domain.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.service.LogService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.coldlake.app.payment.service.LogService.cm;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/13 08:56
 */
@Getter
@AllArgsConstructor
public enum ThirdPartOrderStatusAdapterEnum {
    PAYER_ACTION_REQUIRED(0),
    CREATED(0),
    APPROVED(0),
    COMPLETED(1),


    /**
     * stripe checkout_session payment status
     * <p></p>
     *
     * @see <a href="https://docs.stripe.com/api/checkout/sessions/object">stripe session <a/>
     */
    no_payment_required(0),
    paid(1),
    unpaid(0);

    private final int state;


    private static final Map<String, ThirdPartOrderStatusAdapterEnum> cache = Arrays.stream(com.coldlake.app.payment.domain.paypal.orders.enums.ThirdPartOrderStatusAdapterEnum.values()).collect(Collectors.toMap(Enum::name, Function.identity()));


    public static ThirdPartOrderStatusAdapterEnum ofNullable(String name) {
        return cache.get(name);
    }


    public static ThirdPartOrderStatusAdapterEnum of(String name) {
        return Optional.ofNullable(ofNullable(name)).orElseThrow(() -> new RuntimeException(String.format("%s not found ThirdPartOrderStatusAdapterEnum via string '%s' ", cm(), name)));
    }

    public PaymentOrderStatusEnum convert() {
        return PaymentOrderStatusEnum.ofIdx(this.state);
    }

}
