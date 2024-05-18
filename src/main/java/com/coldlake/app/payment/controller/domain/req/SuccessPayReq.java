package com.coldlake.app.payment.controller.domain.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/19 10:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessPayReq {
    private String tradeNo;

    private String payAmount;

    private String did;

    private String chan;

    private String fbc;

}

