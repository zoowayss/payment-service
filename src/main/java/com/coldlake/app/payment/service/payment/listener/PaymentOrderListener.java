package com.coldlake.app.payment.service.payment.listener;

import com.coldlake.app.payment.domain.payment.PaymentOrder;

/**
 * @Description: 支付订单监听器
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/15 20:36
 */

public interface PaymentOrderListener {

    /**
     * 创建订单 前置处理器
     *
     * @param optional
     */
    default void processPaymentOrderPreCreate(Object optional) {
    }


    /**
     * 创建订单 后置处理器
     *
     * @param paymentOrder
     * @param optional
     */
    default void processPaymentOrderCreated(PaymentOrder paymentOrder, Object optional) {
    }

    /**
     * 查询订单状态 后置处理器
     *
     * @param order
     * @param name
     */
    default void processPostRetrieveOrder(PaymentOrder order, String name) {
    }

    /**
     * 订单被取消 后置处理器
     *
     * @param subscriptionId
     */
    default void processSubscriptionBeCanceled(String subscriptionId) {
    }
}
