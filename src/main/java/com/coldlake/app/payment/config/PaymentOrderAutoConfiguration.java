package com.coldlake.app.payment.config;

import com.coldlake.app.payment.domain.UserToken;
import com.coldlake.app.payment.job.PaymentOrderJob;
import com.coldlake.app.payment.service.PaymentUserService;
import com.coldlake.app.payment.service.payment.PaymentOrderHandler;
import com.coldlake.app.payment.service.payment.callback.PaymentCallbackHandler;
import com.coldlake.app.payment.service.payment.listener.PaymentOrderListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
        };
    }

    @Bean
    @ConditionalOnMissingBean(PaymentUserService.class)
    public PaymentUserService defaultPaymentUserService() {
        return uid -> {
            if (uid == null || uid == 0) {
                return new UserToken(1L, "auto_user_token");
            }
            return new UserToken(2L, "default_token");
        };
    }


    @Bean
    @ConditionalOnProperty(prefix = "payment.order.job", name = "enabled", havingValue = "true", matchIfMissing = true)
    public PaymentOrderJob paymentOrderJob(List<PaymentOrderHandler> paymentOrderHandlers) {
        return new PaymentOrderJob(paymentOrderHandlers);
    }

}
