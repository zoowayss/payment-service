package com.coldlake.app.payment.domain.constants;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/17 16:42
 */

public class Constants {
    public static final long ONE_SECOND_MILL = 1000L;

    /**
     * paypal 支付成功后的回调地址
     */
    public static final String PAYPAL_SUCCESS_URI = "/v1/aichat/successPay?chan=%s&did=%s&fbc=%s&payAmount=%s";

    /**
     * paypal 支付失败后的回调地址
     */
    public static final String PAYPAL_FAILED_URI = "/vip/?fail=1&chan=%s";

    /**
     * stripe 支付成功后的回调地址
     */
    public static final String STRIPE_SUCCESS_URI = "/v1/aichat/stripeSuccessPay?chan=%s&sessionId={CHECKOUT_SESSION_ID}&did=%s&fbc=%s&payAmount=%s";


    /**
     * stripe 支付失败后的回调地址
     */
    public static final String STRIPE_FAILED_URI = "/vip/?fail=1&chan=%s";

    /**
     * apple 支付成功后的回调地址
     */
    public static final String APPLE_SUCCESS_URI = "/v1/aichat/apple/success/pay?chan=%s&did=%s&fbc=%s&payAmount=%s";

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
