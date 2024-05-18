package com.coldlake.app.payment.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 16:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRetrieveOrderWrapper {

    private String uid;

    private String productId;

    private String receiptData;

    private String transactionId;

    private String mod;

    private String purchaseToken;

    private String orderId;
}
