package com.coldlake.app.payment.config;

import com.coldlake.app.payment.domain.IInvoice;
import com.coldlake.app.payment.domain.PaymentOrder;
import com.coldlake.app.payment.domain.paypal.PayPalWebhookEvent;
import com.coldlake.app.payment.service.PaymentOrderHandler;
import com.coldlake.app.payment.service.PaymentOrderService;
import com.coldlake.app.payment.service.handler.callback.PaymentCallbackHandler;
import com.coldlake.app.payment.service.listener.PaymentOrderListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/15 08:42
 */
@Configuration
@Slf4j
@ComponentScan(basePackages = {"com.coldlake.app.payment.**"})
public class PaymentOrderAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(PaymentCallbackHandler.class)
    public PaymentCallbackHandler defalutPaymentCallbackHandler() {
        return new PaymentCallbackHandler() {
            @Override
            public void handleStripeCheckoutSessionCompletedEvent(String orderId, String mode) {
                log.warn("There is not any PaymentCallbackHandler to handleStripeCheckoutSessionCompletedEvent, please implement it if you require.");
            }

            @Override
            public void handleStripeInvoicePaidEvent(String subscriptionId, IInvoice invoiceId, Long paidAt) {
                log.warn("There is not any PaymentCallbackHandler to handleStripeInvoicePaidEvent, please implement it if you require.");
            }

            @Override
            public void handlePaymentSuccessPay(String subscribeId, PayPalWebhookEvent.Resource resource) {
                log.warn("There is not any PaymentCallbackHandler to handlePaymentSuccessPay, please implement it if you require.");
            }

            @Override
            public void handlePayPalPaymentSaleCompleted(String tradeNo, IInvoice resourceId, String createTime, PayPalWebhookEvent.Resource resource) {
                log.warn("There is not any PaymentCallbackHandler to handlePaymentSaleCompleted, please implement it if you require.");
            }

            @Override
            public void handleBillingSubscriptionCancelled(String subscriptionId) {
                log.warn("There is not any PaymentCallbackHandler to handleBillingSubscriptionCancelled, please implement it if you require.");
            }

            @Override
            public void handleStripePaymentIntentPaymentFailedEvent(String invoice, String code, String declineCode) {
                log.warn("There is not any PaymentCallbackHandler to handleStripePaymentIntentPaymentFailedEvent, please implement it if you require.");
            }

            @Override
            public void handleStripeCustomSubscriptionUpdatedEvent(String subscriptionId, String latestInvoiceId) {
                log.warn("There is not any PaymentCallbackHandler to handleStripeCustomSubscriptionUpdatedEvent, please implement it if you require.");
            }
        };
    }


    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper defaultObjectMapper() {
        return new ObjectMapper();
    }


    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate defaultRestTemplate() {
        return new RestTemplate();
    }


    @Bean
    @ConditionalOnMissingBean(PaymentOrderListener.class)
    public PaymentOrderListener defaultPaymentOrderListener() {
        return new PaymentOrderListener() {
            @Override
            public void processPaymentOrderCreated(PaymentOrder paymentOrder, Object optional) {
                log.warn("There is not any processPaymentOrderCreated to handle, please implement it if you require.");
            }

            @Override
            public void processPostRetrieveOrder(PaymentOrder order, String name) {
                log.warn("There is not any processPostRetrieveOrder to handle, please implement it if you require.");
            }

            @Override
            public void processSubscriptionBeCanceled(String subscriptionId) {
                log.warn("There is not any processSubscriptionBeCanceled to handle, please implement it if you require.");
            }
        };
    }


    @Bean
    @ConditionalOnMissingBean
    public PaymentOrderService defaultPaymentOrderService() {
        return new PaymentOrderService() {
            @Override
            public List<PaymentOrder> listUnpaidOrder() {
                log.warn(" listUnpaidOrder@PaymentOrderService is not implemented. Please check whether it needs to be implemented");
                return PaymentOrderService.super.listUnpaidOrder();
            }

            @Override
            public boolean casOrderStatus(String id, String subscriptionId, Integer oldStatus, Integer newStatus) {
                log.warn(" casOrderStatus@PaymentOrderService is not implemented. Please check whether it needs to be implemented. params: id={}, oldStatus={}, newStatus={}", id, oldStatus, newStatus);
                return PaymentOrderService.super.casOrderStatus(id, subscriptionId, oldStatus, newStatus);
            }

            @Override
            public List<PaymentOrder> listWillExpiredMembers() {
                log.warn(" listWillExpiredMembers@PaymentOrderService is not implemented. Please check whether it needs to be implemented. ");
                return PaymentOrderService.super.listWillExpiredMembers();
            }

            @Override
            public List<PaymentOrder> listCancelSubscriptionFailedOrders() {
                log.warn(" listCancelSubscriptionFaildOrders@PaymentOrderService is not implemented. Please check whether it needs to be implemented. ");
                return PaymentOrderService.super.listCancelSubscriptionFailedOrders();
            }

            @Override
            public void updateUserSubscriptionIfNecessary(String subscriptionId, String invoiceId, Long paidAt) {
                log.warn(" updateUserSubscriptionIfNecessary@PaymentOrderService is not implemented. Please check whether it needs to be implemented. ");
            }

            @Override
            public boolean casOrderStatus(String orderId, Integer oldStatus, Integer newStatus) {
                log.warn(" casOrderStatus@PaymentOrderService is not implemented. Please check whether it needs to be implemented. params: orderId={}, oldStatus={}, newStatus={}", orderId, oldStatus, newStatus);
                return PaymentOrderService.super.casOrderStatus(orderId, oldStatus, newStatus);
            }

            @Override
            public void paymentOrderUpdateOrderPaidStatusIfNecessary(String tradeNo, PaymentOrderHandler paymentOrderHandler) {
                log.warn(" paymentOrderUpdateOrderPaidStatusIfNecessary@PaymentOrderService is not implemented. Please check whether it needs to be implemented. ");
            }

            @Override
            public String queryTradeNoBySubscriptionId(String subId) {
                log.warn(" queryTradeNoBySubscriptionId@PaymentOrderService is not implemented. Please check whether it needs to be implemented. ");
                return PaymentOrderService.super.queryTradeNoBySubscriptionId(subId);
            }
        };
    }


//    @Bean
//    @ConditionalOnProperty(prefix = "payment.order.job", name = "enabled", havingValue = "true", matchIfMissing = true)
//    public PaymentOrderJob paymentOrderJob(List<PaymentOrderHandler> paymentOrderHandlers, PaymentOrderService paymentOrderService) {
//        return new PaymentOrderJob(paymentOrderHandlers, paymentOrderService);
//    }

}
