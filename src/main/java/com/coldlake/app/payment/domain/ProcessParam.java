package com.coldlake.app.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/18 20:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessParam {
    private String clientIp;
    private String clientUA;

    private String tradeNo;

    private String paymentUrl;

    private Integer payType;

    private String cycle;

    private String subscriptionId;

    private String planId;

    private String orderId;

    private Long uid;

    private String skuId;

    private String skuName;

    private Long payAmount;

    private Integer duration;

    private String did;

    private String clientSys;

    private Long expireAt;

    private String currency;

    private Integer skuType;

    private List<String> email;
    private Float value;

    private String fbc;
    private String chan;
    private String eventSourceUrl;

    private String orderType;

}
