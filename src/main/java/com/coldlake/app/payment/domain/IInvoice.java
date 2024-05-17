package com.coldlake.app.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/15 08:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IInvoice {

    private String id;

    private String paymentId;
}
