package top.zoowayss.payment.service;

import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;

public interface ThirdPartOrder {

    /**
     * 创建支付订单
     *
     * @param p
     * @return
     */
    default Order createOrder(Product p) {
        return null;
    }


    /**
     * 创建订阅订单
     *
     * @param p
     * @return
     */
    default Order createSubscription(Product p) {
        return null;
    }


    /**
     * 检索支付订单
     *
     * @param tradeNo
     * @return
     */
    default Order retriveOrder(String tradeNo) {
        return null;
    }


    /**
     * 检索订阅订单
     *
     * @param subscriptionId
     * @return
     */
    default Order retrieveSubscription(String subscriptionId) {
        return null;
    }

}
