package com.coldlake.app.payment.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/15 09:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderCreatedPostProcessorWrapper {
    private PaymentOrder paymentOrder;

    private PaymentOrderCreation paymentOrderCreation;

    private HttpServletRequest request;
}
