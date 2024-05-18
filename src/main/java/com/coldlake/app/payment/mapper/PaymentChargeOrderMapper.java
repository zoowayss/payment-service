package com.coldlake.app.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coldlake.app.payment.entity.PaymentChargeOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentChargeOrderMapper extends BaseMapper<PaymentChargeOrder> {
}