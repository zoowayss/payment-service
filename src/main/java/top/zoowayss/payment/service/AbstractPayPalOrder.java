package top.zoowayss.payment.service;

import jakarta.annotation.Resource;
import top.zoowayss.payment.service.paypal.PaypalClient;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/9 09:48
 */

public class AbstractPayPalOrder implements ThirdPartOrder {

    @Resource
    protected PaypalClient paypalClient;
}
