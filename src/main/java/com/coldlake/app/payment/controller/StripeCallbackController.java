package com.coldlake.app.payment.controller;

import com.coldlake.app.payment.service.payment.stripe.AbstractStripeOrderHandler;
import com.coldlake.app.payment.service.payment.stripe.StripeService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 09:02
 */
@RestController
@ConditionalOnBean(AbstractStripeOrderHandler.class)
@Slf4j
public class StripeCallbackController {


    @Resource
    private StripeService stripeService;

    @Value("${payment.stripe.webhook-secret}")
    private String stripeWebhookSecret;

    /**
     * stripe webhook 异步回调
     *
     * @throws Exception
     */
    @PostMapping(value = "/{version}/callback/payNotifyForStripe", produces = APPLICATION_JSON_UTF8_VALUE)
    public String payNotifyForStripe(@RequestBody String payload,
                                     @RequestHeader("Stripe-Signature") String sigHeader,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        String cm = "payNotifyForStripe@CallbackController";
        log.info(cm + "Stripe notify>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + payload);
        try {
            Webhook.constructEvent(payload, sigHeader, stripeWebhookSecret);
            Integer status = stripeService.handleWebhook(payload, sigHeader, request);
            response.setStatus(status);
            return "success";
        } catch (SignatureVerificationException e) {
            log.error(cm + "Error verifying webhook signature", e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "Error verifying webhook signature";
        } catch (Exception e) {
            log.error("{} error. payload: {}", cm, payload, e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return e.getMessage();
        }

    }
}
