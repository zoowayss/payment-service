package com.coldlake.app.payment.domain.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum PaymentIntentStateEnum {

    CREATE(0, "CREATE", "APPROVAL_PENDING,PAYER_ACTION_REQUIRED,unpaid"),
    PAID(1, "PAID", "ACTIVE,APPROVED,paid,active,COMPLETED"),
    PAY_FAILED(2, "PAY_FAILED", "CANCELED,canceled,Cancelled,CANCELLED"),
    REFUND(3, "REFUND", "");

    private int idx;

    private String name;

    private String thirdState;

    public static PaymentIntentStateEnum of(String thirdState) {
        return Arrays.stream(PaymentIntentStateEnum.values()).filter(v -> Arrays.asList(v.getThirdState().split(",")).contains(thirdState))
                .findFirst().orElseThrow(() -> new RuntimeException(String.format("%s third status not found", thirdState)));
    }
}
