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
    default Order createOrder(Product p) throws Exception {
        return null;
    }


    /**
     * 检索支付订单
     *
     * @param tradeNo
     * @return
     */
    default Order retriveOrder(String tradeNo) throws Exception {
        return null;
    }

    /**
     * 取消订单
     *
     * @param tradeNo
     * @throws Exception
     */
    default void cancelOrder(String tradeNo) throws Exception {
    }

}
