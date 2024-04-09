package top.zoowayss.payment.service.paypal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zoowayss.payment.domain.Order;
import top.zoowayss.payment.domain.Product;
import top.zoowayss.payment.domain.enums.OrderStatus;
import top.zoowayss.payment.domain.paypal.ShippingAmount;
import top.zoowayss.payment.domain.paypal.orders.PayPalOrders;
import top.zoowayss.payment.domain.paypal.orders.PurchaseUnits;

import java.util.Collections;

@Service
@Slf4j
public class PaypalOrder extends AbstractPayPalOrder {


    @Override
    public Order createOrder(Product p) throws Exception {

        PayPalOrders payPalOrders = new PayPalOrders();
        payPalOrders.setPurchaseUnits(Collections.singletonList(new PurchaseUnits(new ShippingAmount(p.getCurrency(), String.format("%.2f", Double.parseDouble(p.getAmount()) / 100)))));
        payPalOrders.setIntent("CAPTURE");
        PayPalOrders orders = paypalClient.orderCreate(payPalOrders);

        return new Order(orders.getId(), OrderStatus.of(orders.getStatus()), orders.getLinks().stream().filter(l -> "payer-action".equals(l.getRel())).findFirst().orElseThrow().getHref());
    }

    @Override
    public Order retriveOrder(String tradeNo) throws Exception {
        PayPalOrders orders = paypalClient.retrieveOrder(tradeNo);
        return new Order(orders.getId(), OrderStatus.of(orders.getStatus()), "payer-action", orders.getLinks());
    }
}
