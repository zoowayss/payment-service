package com.coldlake.app.payment.service.payment;


import com.coldlake.app.payment.domain.payment.AppRetrieveOrderWrapper;
import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.PaymentOrderCreation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 第三方订单管理器 主要用于创建订单、创建订阅、查询订单状态、取消订阅等
 */
public interface PaymentOrderHandler extends NamedHandler {
    String METADATA_FIELD_ORDER_ID = "orderId";


    Logger log = LoggerFactory.getLogger(PaymentOrderHandler.class);


    /**
     * 检索支付订单
     *
     * @param tradeNo
     * @return
     */
    default PaymentOrder retrieveOrder(String tradeNo) throws Exception {
        return new PaymentOrder();
    }

    default PaymentOrder retrieveSubscription(String subId) throws Exception {
        return new PaymentOrder();
    }

    /**
     * 检索支付订单
     *
     * @param tradeNo
     * @return
     */
    default PaymentOrder retrieveOrderWithListener(String tradeNo) throws Exception {
        return null;
    }

    /**
     * 取消订单
     *
     * @param tradeNo
     * @throws Exception
     */
    default void cancelSubscription(String tradeNo, String reason) throws Exception {
    }


    default PaymentOrder createPaymentOrder(PaymentOrderCreation p, Object optional) throws Exception {
        return null;
    }

    /**
     * 取消订阅 还会调用 PaymentSubscriptionCanceledPostProcessor
     *
     * @param subscriptionId 订阅id
     * @param reason         取消原因
     * @throws Exception
     */
    default void cancelSubscriptionWithListener(String subscriptionId, String reason) throws Exception {
    }


    /**
     * APP 支付方式查单
     *
     * @param orderWrapper
     * @return
     * @throws Exception
     */
    default PaymentOrder successPay(AppRetrieveOrderWrapper orderWrapper) throws Exception {
        String cm = "successPay@PaymentOrderHandler";
        log.warn("{} not implement", cm);
        return null;
    }

    /**
     * 获取成功的uri
     *
     * @return
     */
    String getSuccessUri();

    /**
     * 获取失败的uri
     *
     * @return
     */
    String getFailureUri();
}
