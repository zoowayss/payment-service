package com.coldlake.app.payment.controller;

import com.coldlake.app.payment.domain.payment.paypal.PayPalWebhookEvent;
import com.coldlake.app.payment.service.payment.paypal.AbstractPayPalOrderHandler;
import com.coldlake.app.payment.service.payment.paypal.PaypalService;
import com.coldlake.app.payment.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.coldlake.app.payment.service.payment.LogService.cm;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 14:00
 */
@RestController
@ConditionalOnBean(AbstractPayPalOrderHandler.class)
@Slf4j
public class PayPalCallbackController {

    @Resource
    private PaypalService paypalService;

    /**
     * paypal webhook 异步回调
     * <a href=https://developer.paypal.com/docs/api/webhooks/v1/#verify-webhook-signature_post>Paypal文档</a>
     *
     * @param body 必须用Map<String,Object>，用 对象 序列化反序列化之后，可能会导致属性丢失；用 String 接收转为 Json 时也和 Paypal 给的不一样。 踩坑！！！
     * @throws Exception
     */
    @PostMapping(value = "/{version}/callback/payNotifyForPaypal", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void payNotifyForPaypal(@RequestBody Map<String, Object> body,
                                   @RequestHeader("PAYPAL-AUTH-ALGO") String authLog,
                                   @RequestHeader("PAYPAL-CERT-URL") String certUrl,
                                   @RequestHeader("PAYPAL-TRANSMISSION-ID") String transmissionId,
                                   @RequestHeader("PAYPAL-TRANSMISSION-SIG") String transmissionSig,
                                   @RequestHeader("PAYPAL-TRANSMISSION-TIME") String transmissionTime, HttpServletResponse response) throws Exception {
        String cm = cm();
        log.info("{} receive PayPal notify data: {}", cm, JsonUtils.logJson(body));

        boolean isValid = paypalService.webhookValidate(body, authLog, certUrl, transmissionId, transmissionSig, transmissionTime);
        if (!isValid) {
            log.error("webhook validate fail. body:{}", body);
            return;
        }
        try {
            PayPalWebhookEvent webhookEvent = JsonUtils.toObject(JsonUtils.toJson(body), PayPalWebhookEvent.class);
            paypalService.dealOrderEvent(webhookEvent);
        } catch (Exception e) {
            log.error("{}", cm, e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
