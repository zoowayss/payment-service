package com.coldlake.app.payment.service.paypal;

import com.coldlake.app.payment.domain.constants.Constants;
import com.coldlake.app.payment.service.AbstractPaymentHandler;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 09:48
 */

public abstract class AbstractPayPalOrderHandler extends AbstractPaymentHandler {

    public static final String ABSTRACT_PAYPAL_ORDER = "ABSTRACT_PAYPAL_PAYMENT_ORDER";
    @Resource
    protected PaypalClient paypalClient;

    @Override
    public String getName() {
        return ABSTRACT_PAYPAL_ORDER;
    }

    public static Long getCurrentTimeStamp() {
        return Instant.now().toEpochMilli();
    }

    protected String getPlanStartTime(String cycle) {
        Long subStartTime = 0L;
        switch (cycle) {
            case "WEEK":
                subStartTime = getCurrentTimeStamp() + 6 * 24 * 60 * 60 * 1000L;
                break;
            case "YEAR":
                subStartTime = getCurrentTimeStamp() + 364 * 24 * 60 * 60 * 1000L;
                break;
            case "MONTH":
                subStartTime = getCurrentTimeStamp() + 29 * 24 * 60 * 60 * 1000L;
                break;
            case "Weekly-Test":
                subStartTime = getCurrentTimeStamp() + 4 * 60 * 1000L;
                break;
            default:
                subStartTime = getCurrentTimeStamp() + 10 * 364 * 24 * 60 * 60 * 1000L;
        }
        // 使用Instant将毫秒时间戳转换为UTC时区的时间点
        Instant instant = Instant.ofEpochMilli(subStartTime);

        // 定义日期时间格式化器，以符合ISO 8601格式，末尾添加'Z'表示UTC时区
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);

        // 格式化时间点为字符串

        return formatter.format(instant);
    }

    @Override
    public String getSuccessUri() {
        return Constants.PAYPAL_SUCCESS_URI;
    }

    @Override
    public String getFailureUri() {
        return Constants.PAYPAL_FAILED_URI;
    }
}
