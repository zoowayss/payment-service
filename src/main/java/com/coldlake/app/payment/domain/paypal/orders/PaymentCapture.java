package com.coldlake.app.payment.domain.paypal.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.coldlake.app.payment.domain.paypal.orders.enums.ThirdPartOrderStatusAdapterEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/13 09:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCapture {
    private String id;

    private ThirdPartOrderStatusAdapterEnum status;
}
