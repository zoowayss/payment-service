package com.coldlake.app.payment.domain.apple;

import lombok.Data;

import java.util.List;

@Data
public class UnifiedReceipt {
    private Integer status;

    private String environment;

    private String latest_receipt;

    private List<LatestReceiptInfo> latest_receipt_info;
}
