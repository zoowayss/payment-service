package com.coldlake.app.payment.controller.domain.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.coldlake.app.payment.domain.payment.constants.Constants.SKU_SOURCE_TYPE_STRIPE;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateOrderReq {

    String sku = "";

    Integer source = SKU_SOURCE_TYPE_STRIPE;

    String currency = "";

    String cancelUrl = "";

    String successUrl = "";

    String nickname = "";

    String email = "";

    String fbc = "";

    String chan = "";
}
