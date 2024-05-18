package com.coldlake.app.payment.service.payment.paypal;

import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.PaymentOrderCreation;
import com.coldlake.app.payment.domain.payment.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.domain.payment.enums.PaymentTypeEnum;
import com.coldlake.app.payment.domain.payment.paypal.orders.PayPalOrders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("paypalOrderHandler")
@Slf4j
public class PaypalOrderHandler extends AbstractPayPalOrderHandler {

    public static final String PAYPAL_ORDER = "PAYPAL_ORDER";


    @Override
    public PaymentOrder createOrder(PaymentOrderCreation p) throws Exception {

        PayPalOrders payPalOrders = new PayPalOrders();
        payPalOrders.setPurchaseUnits(Collections.singletonList(new PayPalOrders.PurchaseUnit(new PayPalOrders.Amount(p.getCurrency(), String.format("%.2f", Double.valueOf(p.getAmount()) / 100)))));
        payPalOrders.setIntent("CAPTURE");
        PayPalOrders orders = paypalClient.orderCreate(payPalOrders, p.getHost(), p.getSuccessUrlParams(), p.getCancelUrlParams());
        return PaymentOrder.newPaypalOrder(orders.getId(), PaymentTypeEnum.PAYPAL_ORDER, "", PaymentOrderStatusEnum.ofIdx(orders.getStatus()
                .getState()), "payer-action", orders.getLinks());
    }

    @Override
    public PaymentOrder retrieveOrder(String tradeNo) throws Exception {
        String cm = "retrieveOrder@PaypalOrder";
        log.info(cm + " tradeNo:{}", tradeNo);
        PayPalOrders orders = paypalClient.retrieveOrder(tradeNo);
        log.info(cm + " orders:{}", orders);
        return PaymentOrder.newPaypalOrder(orders.getId(), PaymentTypeEnum.PAYPAL_ORDER, "", PaymentOrderStatusEnum.ofIdx(orders.getStatus()
                .getState()), "payer-action", orders.getLinks());
    }

    @Override
    public String getName() {
        return PAYPAL_ORDER;
    }

}
