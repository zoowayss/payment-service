package com.coldlake.app.payment.anno;

import com.coldlake.app.payment.config.PaymentOrderAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Documented
@Target({java.lang.annotation.ElementType.TYPE})
@Import({PaymentOrderAutoConfiguration.class})
public @interface EnablePayment {}
