package com.coldlake.app.payment.service;

import com.coldlake.app.payment.domain.UserToken;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/19 12:45
 */
public interface PaymentUserService {
    UserToken generateToken(Long uid);
}
