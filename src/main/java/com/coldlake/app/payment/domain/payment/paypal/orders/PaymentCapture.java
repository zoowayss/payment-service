package com.coldlake.app.payment.domain.payment.paypal.orders;

import com.coldlake.app.payment.domain.payment.paypal.orders.enums.ThirdPartOrderStatusAdapterEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
