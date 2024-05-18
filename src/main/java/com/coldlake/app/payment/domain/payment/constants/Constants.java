package com.coldlake.app.payment.domain.payment.constants;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/17 16:42
 */

public class Constants {
    public static final long ONE_SECOND_MILL = 1000L;

    /**
     * 一分钟 毫秒数
     */
    public static final long ONE_MINUTE_MILL = 60 * ONE_SECOND_MILL;

    /**
     * 半小时 毫秒数
     */
    public static final long HALF_HOUR_MILLIS = 30 * ONE_MINUTE_MILL;

    /**
     * 货币代码 刀乐
     */
    public static final String USD = "USD";


    /**
     * https
     */
    public static final String HTTPS_PREFIX = "https://";

    public static final int SKU_SOURCE_TYPE_STRIPE = 1;

    /**
     * paypal 支付成功后的回调地址
     */
    public static final String PAYPAL_SUCCESS_URI = "/v1/%s/order/%d/success/pay?chan=%s&tradeNo={CHECKOUT_SESSION_ID}&did=%s&fbc=%s&payAmount=%s";

    /**
     * paypal 支付失败后的回调地址
     */
    public static final String PAYPAL_FAILED_URI = "/vip/?fail=1&chan=%s";

    /**
     * stripe 支付成功后的回调地址
     */
    public static final String STRIPE_SUCCESS_URI = "/v1/%s/order/%d/success/pay?chan=%s&tradeNo={CHECKOUT_SESSION_ID}&did=%s&fbc=%s&payAmount=%s";


    /**
     * stripe 支付失败后的回调地址
     */
    public static final String STRIPE_FAILED_URI = "/vip/?fail=1&chan=%s";

    /**
     * apple 支付成功后的回调地址
     */
    public static final String APPLE_SUCCESS_URI = "/v1/aichat/apple/success/pay?chan=%s&did=%s&fbc=%s&payAmount=%s&orderId=%s";

    /**
     * apple 支付失败后的回调地址
     */
    public static final String APPLE_FAILED_URI = "/vip/?fail=1&chan=%s";


    /**
     * apple 支付成功后的回调地址
     */
    public static final String GOOGLE_SUCCESS_URI = "/v1/aichat/apple/success/pay?chan=%s&did=%s&fbc=%s&payAmount=%s";

    /**
     * apple 支付失败后的回调地址
     */
    public static final String GOOGLE_FAILED_URI = "/vip/?fail=1&chan=%s";
}
