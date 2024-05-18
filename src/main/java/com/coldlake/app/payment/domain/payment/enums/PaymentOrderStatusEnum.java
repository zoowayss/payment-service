package com.coldlake.app.payment.domain.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.coldlake.app.payment.service.payment.LogService.cm;


@AllArgsConstructor
@Getter
public enum PaymentOrderStatusEnum {

    CREATE(0, "CREATE"),

    ACTIVE(1, "ACTIVE"),

    CANCELED(2, "CANCELED"),

    EXPIRED(3, "EXPIRED"),

    SUB_NOTFOUND(4, "SUB_NOTFOUND");

    private Integer idx;

    private String name;


    private static final Map<Integer, PaymentOrderStatusEnum> cache = Arrays.stream(PaymentOrderStatusEnum.values())
            .collect(Collectors.toMap(PaymentOrderStatusEnum::getIdx, Function.identity()));

    public static PaymentOrderStatusEnum ofIdx(Integer idx) {
        return Optional.ofNullable(cache.get(idx))
                .orElseThrow(() -> new RuntimeException(String.format("%s not found PaymentOrderStatusEnum via idx: %d", cm(), idx)));
    }

}