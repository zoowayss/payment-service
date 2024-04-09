package top.zoowayss.payment.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@AllArgsConstructor
@Getter
public enum OrderStatusEnum {

    CREATE(0, "CREATE", "APPROVAL_PENDING,PAYER_ACTION_REQUIRED,unpaid"),
    ACTIVE(1, "ACTIVE", "ACTIVE,APPROVED,paid"),
    CANCELED(2, "CANCELED", "CANCELED"),
    COMPLETED(3, "COMPLETED", "COMPLETED");
    private Integer idx;
    private String desc;
    private String thirdState;


    public static OrderStatusEnum of(String thirdState) {
        return Arrays.stream(OrderStatusEnum.values()).filter(v -> Arrays.asList(v.getThirdState().split(",")).contains(thirdState))
                .findFirst().orElseThrow(() -> new RuntimeException(String.format("%s third status not found", thirdState)));
    }

}