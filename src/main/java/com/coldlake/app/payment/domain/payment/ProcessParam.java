package com.coldlake.app.payment.domain.payment;

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
    /**
     * 用户id
     */
    private Long uid;

    /**
     * 用户设备id
     */
    private String did;

    /**
     * 用户 ip
     */
    private String clientIp;

    /**
     * 用户浏览器 ua
     */
    private String clientUA;

    /**
     * 用户客户端类型 android ios ...
     */
    private String clientSys;

    /**
     * 订阅类 sku 订阅多长时间
     */
    private Integer duration;

    /**
     * facebook 上报参数
     */
    private String fbc;

    /**
     * facebook 上报参数
     */
    private String eventSourceUrl;

    /**
     * 渠道参数
     */
    private String chan;

    /**
     * 订单类型
     * <p></p>
     *
     * @see com.soulfriendship.wsd.calorie.common.payment.service.PaymentOrderHandler.getName()
     */
    private String orderType;

    /**
     * 第三方 sku id
     */
    private String skuId;

    /**
     * sku 名称
     */
    private String skuName;

    /**
     * sku 价格 单位： 分
     */
    private Long amount;

    /**
     * paypal 订阅会用到 计算下一次订阅开始时间
     */
    private String cycle;

    /**
     * 业务参数
     */
    private String bid;

    /**
     * 货币代码 如：usd 刀乐
     */
    private String currency;

    /**
     * stripe 下单参数
     */
    private Long quantity;

    /**
     * chargeOrder 表 id
     */
    private String orderId;

    /**
     * 哪个域名来的请求
     */
    private String host;

    /**
     * 付款链接过期时间
     */
    private Long expiresAt;


    /**
     * 支付成功回调参数
     */
    private String successUrlParams;

    /**
     * 支付失败回调参数
     */
    private String cancelUrlParams;

    /**
     * facebook 上报用户邮箱
     */
    private List<String> email;


}
