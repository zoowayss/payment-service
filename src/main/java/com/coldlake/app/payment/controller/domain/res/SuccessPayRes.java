package com.coldlake.app.payment.controller.domain.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/19 10:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessPayRes {

    private String token;
}
