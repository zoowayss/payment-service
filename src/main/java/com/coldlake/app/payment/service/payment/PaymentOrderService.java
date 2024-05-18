package com.coldlake.app.payment.service.payment;

import com.coldlake.app.payment.controller.domain.req.SuccessPayReq;
import com.coldlake.app.payment.controller.domain.res.SuccessPayRes;
import com.coldlake.app.payment.controller.domain.vo.CreateOrderVo;
import com.coldlake.app.payment.domain.payment.ProcessParam;
import com.coldlake.app.payment.domain.payment.SkuConfItem;
import com.coldlake.app.payment.entity.PaymentChargeOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    List<PaymentChargeOrder> listUnpaidOrder();

    SkuConfItem validateSkuInRequest(String sku, boolean isDev, Integer source);

    SuccessPayRes doSuccessPay(SuccessPayReq successPayReq, PaymentOrderHandler paymentOrderHandler);

    CreateOrderVo doCreatePayOrder(PaymentOrderHandler paymentOrderHandler, ProcessParam processParam);

    boolean casOrderStatus(String paymentChargeOrderId, Integer oldStatus, Integer newStatus);
}
