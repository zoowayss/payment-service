package com.coldlake.app.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coldlake.app.payment.domain.payment.SkuConfItem;
import com.coldlake.app.payment.entity.PaymentChargeOrder;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/19 11:03
 */
public interface PaymentChargeOrderService extends IService<PaymentChargeOrder> {

    boolean casOrderStatus(String paymentChargeOrderId, Integer oldStatus, Integer newStatus);

    SkuConfItem validateSkuInRequest(String sku, boolean isDev, Integer source);

    PaymentChargeOrder getByTradeNo(String tradeNo);
}
