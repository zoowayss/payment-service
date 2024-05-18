package com.coldlake.app.payment;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/19 07:55
 */
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableScheduling
public class PaymentLauncher {
    public static void main(String[] args) {
        SpringApplication.run(PaymentLauncher.class, args);
    }
}
