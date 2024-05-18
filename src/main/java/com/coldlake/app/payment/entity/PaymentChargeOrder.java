package com.coldlake.app.payment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户充值订单表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "payment_charge_order")
public class PaymentChargeOrder implements Serializable {

    private static final long serialVersionUID = 8360227147006561080L;

    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 用户UID
     */
    @TableField(value = "`uid`")
    private Long uid;

    /**
     * 商品id
     */
    @TableField(value = "sku_id")
    private String skuId;

    /**
     * 下单时的商品名称
     */
    @TableField(value = "sku_name")
    private String skuName;

    /**
     * 会员天数
     */
    @TableField(value = "duration")
    private Integer duration;

    /**
     * 下单时商品的价格，单位：分
     */
    @TableField(value = "amount")
    private Long amount;

    /**
     * 订单支付金额，单位：分
     */
    @TableField(value = "pay_amount")
    private Long payAmount;

    /**
     * 支付交易号
     */
    @TableField(value = "trade_no")
    private String tradeNo;

    /**
     * 订阅id
     */
    @TableField(value = "subscription_id")
    private String subscriptionId;

    /**
     * 账单id，stripe才有
     */
    @TableField(value = "invoice")
    private String invoice;

    /**
     * paypal一次性支付：PAYPAL_ORDER    paypal订阅：PAYPAL_SUBSCRIBE  Stripe一次性支付：STRIPE_ORDER  stripe 订阅：STRIPE_SUBSCRIPTION Apple一次性支付：APPLE_ORDER Apple订阅：APPLE_SUBSCRIPTION Goole一次性支付：GOOLE_ORDER goole订阅：GOOLE_SUBSCRIPTION
     */
    @TableField(value = "order_type")
    private String orderType;

    /**
     * 状态，0：未支付，1：已支付，2: 已过期
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 客户端系统类型
     */
    @TableField(value = "client_sys")
    private String clientSys;

    /**
     * 业务bid
     */
    @TableField(value = "bid")
    private String bid;

    /**
     * 用户设备did
     */
    @TableField(value = "did")
    private String did;

    /**
     * 用户操作IP
     */
    @TableField(value = "client_ip")
    private String clientIp;

    /**
     * 支付时间，单位：毫秒
     */
    @TableField(value = "pay_time")
    private Long payTime;

    /**
     * 创建时间，单位：毫秒
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Long createTime;

    /**
     * 更新时间，单位：毫秒
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    /**
     * 订单过期时间，默认： 30min
     */
    @TableField(value = "expire_time")
    private Long expireTime;

    /**
     * 是否首冲，0为否，1为是
     */
    @TableField(value = "`first`")
    private Boolean first;

    /**
     * 支付货币代码（由3个字母组成)
     */
    @TableField(value = "currency")
    private String currency;

    /**
     * sku类型，0为测试，1为30分钟前首冲，2为30分钟后首冲，3为普通，4为非首冲，5为一次性订阅会员
     */
    @TableField(value = "sku_type")
    private Byte skuType;

    /**
     * 最近一次续费renew的更新时间
     */
    @TableField(value = "latest_renew_time")
    private Long latestRenewTime;

    /**
     * 取消续费的时间
     */
    @TableField(value = "renew_cancel_time")
    private Long renewCancelTime;

    /**
     * 支付链接
     */
    @TableField(value = "payment_url")
    private String paymentUrl;

}