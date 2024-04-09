package top.zoowayss.payment.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@AllArgsConstructor
@Getter
 public enum OrderStatus {

    CREATE(0, "CREATE", "APPROVAL_PENDING"),
    ACTIVE(1, "ACTIVE", "ACTIVE"),
    CANCELED(2, "CANCELED", "CANCELED"),
    COMPLETED(3, "COMPLETED", "COMPLETED");
    private Integer state;
    private String desc;
    private String thirdState;


    public static OrderStatus of(String thirdState) {
        return Arrays.stream(OrderStatus.values()).filter(v -> v.getThirdState().contains(thirdState)).findFirst().orElseThrow(() -> new RuntimeException(String.format("%s third status not found", thirdState)));
    }

}