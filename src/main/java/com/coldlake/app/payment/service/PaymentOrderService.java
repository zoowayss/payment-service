package com.coldlake.app.payment.service;

import com.coldlake.app.payment.domain.PaymentOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/15 11:34
 */

public interface PaymentOrderService {

    Logger log = LoggerFactory.getLogger(PaymentOrderService.class);

    /**
     * 查询未支付订单
     *
     * @return com.soulfriendship.wsd.plant.payment.domain.PaymentOrder id 取 tradeNo . supportPaymentOrderManager 也需要返回
     */
    default List<PaymentOrder> listUnpaidOrder() {
        return Collections.emptyList();
    }

    /**
     * cas 操作订单状态
     *
     * @param id
     * @param oldStatus
     * @param newStatus
     */
    default boolean casOrderStatus(String id, String subscriptionId, Integer oldStatus, Integer newStatus) {
        return false;
    }

    /**
     * 查询将要过期的会员 这里只查询订阅形式的会员
     *
     * @return com.soulfriendship.wsd.plant.payment.domain.PaymentOrder id 取 tradeNo . supportPaymentOrderManager 也需要返回
     */
    default List<PaymentOrder> listWillExpiredMembers() {
        return Collections.emptyList();
    }

    /**
     * 查询取消订阅失败的订单
     *
     * @return com.soulfriendship.wsd.plant.payment.domain.PaymentOrder supportPaymentOrderManager 也需要返回
     */
    default List<PaymentOrder> listCancelSubscriptionFailedOrders() {
        return Collections.emptyList();
    }

    /**
     * 如果需要的话 给订阅用户加会员时间
     *
     * @param subscriptionId
     * @param invoiceId
     * @param paidAt
     */
    default void updateUserSubscriptionIfNecessary(String subscriptionId, String invoiceId, Long paidAt) {
    }

    default boolean casOrderStatus(String orderId, Integer oldStatus, Integer newStatus) {
        return false;
    }

    default void paymentOrderUpdateOrderPaidStatusIfNecessary(String tradeNo, PaymentOrderHandler paymentOrderHandler) {}

    default String queryTradeNoBySubscriptionId(String subId) {
        return null;
    }
}
