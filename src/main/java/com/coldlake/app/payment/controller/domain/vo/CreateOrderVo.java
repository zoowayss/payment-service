package com.coldlake.app.payment.controller.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderVo {


    /**
     * 支付链接
     */
    private String href;

    /**
     * 订单Id
     */
    private String orderId;

}
