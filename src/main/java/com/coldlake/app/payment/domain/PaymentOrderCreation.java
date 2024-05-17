package com.coldlake.app.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderCreation {

    /**
     * 订单表 id
     */
    private String orderId;

    /**
     * 提交到第三方支付机构的 skuId
     */
    private String id;

    /**
     * 支付货币
     */
    private String currency;

    /**
     * 订单价格 单位： 分
     */
    private Long amount;


    /**
     * 数量
     */
    private Long quantity;

    /**
     * Paypal 订阅用到
     * <p>用来计算下一次开始扣费时间</p>
     */
    private String cycle;

    /**
     * 域名 单纯的域名 用来拼接回调 url
     */
    private String host;

    /**
     * 用来拼接支付成功回调 url
     */
    private String successUrlParams;


    /**
     * 用来拼接取消支付回调 url
     */
    private String cancelUrlParams;

    /**
     * 支付过期时间 单位：毫秒
     */
    private Long expiresAt;

    private Long uid;
    private String skuId;
    private String skuName;
    private Long skuAmount;
    private Integer skuType;
    private Integer skuDuration;
    private String ip;
    private String did;
    private String clientSys;
    private Integer payType;
    private String planId;
    private Long payAmount;
    private String tradeNo;


    private HttpServletRequest request;
}
