package com.coldlake.app.payment.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkuConfItem {

    public static final int LIFE_TIME_TYPE = 5;

    //标识id
    String sku = "";

    // skuId
    String skuId = "";

    //价格，单位：分
    Long amount = 0L;

    //会员天数
    Integer duration = 0;

    //sku名称
    String name = "";

    //sku类型，paypal 一次性支付  paypal 订阅   stripe 一次性支付  stripe 订阅 .....
    Integer type = 0;

    //商品种类，0为未知，1为可购买单个，2为可购买多个
    Integer kind = 0;

    // SKU订阅周期 允许的值：“WEEK”，“DAY”，“YEAR”，“MONTH”
    String cycle = "YEAR";

}
