package com.coldlake.app.payment.controller;

import com.coldlake.app.payment.service.payment.apple.AppleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/15 16:17
 */
@RestController
@Slf4j
public class AppleCallbackController {


    @Resource
    private AppleService appleService;

    @PostMapping(value = "/{version}/callback/apple", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String appleNotify(@RequestBody String inReq) {
        String cm = "appleNotify@CallbackController";
        log.info("{} inReq:{}", cm, inReq);
        try {
            appleService.handleAppleSubscriptionNotify(inReq);
        } catch (Exception e) {
            log.info(cm + " error: ", e);
        }


        return "success";
    }
}
