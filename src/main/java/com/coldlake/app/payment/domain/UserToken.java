package com.coldlake.app.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/19 11:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    private Long uid;

    private String token;
}
