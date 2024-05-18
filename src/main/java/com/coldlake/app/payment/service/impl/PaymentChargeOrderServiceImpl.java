package com.coldlake.app.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coldlake.app.payment.controller.domain.ex.BusinessException;
import com.coldlake.app.payment.domain.payment.PaymentOrder;
import com.coldlake.app.payment.domain.payment.ProcessParam;
import com.coldlake.app.payment.domain.payment.SkuConfItem;
import com.coldlake.app.payment.domain.payment.constants.Constants;
import com.coldlake.app.payment.entity.PaymentChargeOrder;
import com.coldlake.app.payment.mapper.PaymentChargeOrderMapper;
import com.coldlake.app.payment.service.PaymentChargeOrderService;
import com.coldlake.app.payment.service.payment.listener.PaymentOrderListener;
import com.coldlake.app.payment.service.payment.listener.PaymentSuccessPayOnlyExecOnceListener;
import com.coldlake.app.payment.utils.JsonUtils;
import com.coldlake.app.payment.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.coldlake.app.payment.controller.domain.enums.ResultCodeEnum.SKU_NOT_FOUND;
import static com.coldlake.app.payment.service.payment.LogService.cm;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/19 11:03
 */
@Service
@Slf4j
public class PaymentChargeOrderServiceImpl extends ServiceImpl<PaymentChargeOrderMapper, PaymentChargeOrder> implements PaymentChargeOrderService, PaymentSuccessPayOnlyExecOnceListener, PaymentOrderListener {

    private List<SkuConfItem> getSkuList(boolean devEnv, int source) {
        String cm = cm();
        try {
            InputStream inFs = null;
            if (Constants.SKU_SOURCE_TYPE_STRIPE == source) {
                inFs = new ClassPathResource("calorie_stripe_sku.json").getInputStream();
            }

            // defalut stripe
            if (inFs == null) {
                inFs = new ClassPathResource("calorie_stripe_sku.json").getInputStream();
            }

            String fStr = StreamUtils.copyToString(inFs, StandardCharsets.UTF_8);
            Map<String, Object> obj = JSON.parseObject(fStr, Map.class);
            String key = devEnv ? "dev" : "prod";
            if (obj.get(key) == null) {
                return null;
            }

            List<SkuConfItem> rfcs = JSON.parseArray(obj.get(key).toString(), SkuConfItem.class);
            log.info("{} rfcs: {}", cm, rfcs);
            return rfcs;
        } catch (Exception e) {
            log.info("{} error ", cm, e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean casOrderStatus(String paymentChargeOrderId, Integer oldStatus, Integer newStatus) {
        PaymentChargeOrder update = new PaymentChargeOrder();
        update.setStatus(newStatus);
        return super.update(update, new LambdaUpdateWrapper<PaymentChargeOrder>().eq(PaymentChargeOrder::getId, paymentChargeOrderId)
                .eq(PaymentChargeOrder::getSubscriptionId, oldStatus));
    }

    @Override
    public SkuConfItem validateSkuInRequest(String sku, boolean isDev, Integer source) {
        // check sku in request
        List<SkuConfItem> items = this.getSkuList(isDev, source);
        Map<String, SkuConfItem> itemMap = items.stream().collect(Collectors.toMap(SkuConfItem::getSku, SkuConfItem -> SkuConfItem));
        SkuConfItem skuConfItem = itemMap.get(sku);
        if (skuConfItem == null) {
            throw new BusinessException(SKU_NOT_FOUND);
        }
        return skuConfItem;
    }


    @Override
    public PaymentChargeOrder getByTradeNo(String tradeNo) {
        return super.getOne(new LambdaQueryWrapper<PaymentChargeOrder>().eq(PaymentChargeOrder::getTradeNo, tradeNo));
    }

    @Override
    public void onSuccessPay(PaymentChargeOrder order) {
        PaymentChargeOrder update = new PaymentChargeOrder();
        update.setUid(order.getUid());
        update.setPayAmount(order.getAmount());
        update.setPayTime(TimeUtils.getCurrentTimeMils());
        update.setLatestRenewTime(TimeUtils.getCurrentTimeMils());
        super.update(update, new LambdaUpdateWrapper<PaymentChargeOrder>().eq(PaymentChargeOrder::getId, order.getId()));
    }

    @Override
    public void processPaymentOrderCreated(PaymentOrder paymentOrder, Object optional) {
        String cm = cm();
        if (optional instanceof ProcessParam) {
            ProcessParam processParam = (ProcessParam) optional;

            PaymentChargeOrder saveEntity = new PaymentChargeOrder();
            saveEntity.setId(processParam.getOrderId());
            saveEntity.setSkuId(processParam.getSkuId());
            saveEntity.setSkuName(processParam.getSkuName());
            saveEntity.setDuration(processParam.getDuration());
            saveEntity.setAmount(processParam.getAmount());
            saveEntity.setTradeNo(paymentOrder.getTradeNo());
            saveEntity.setOrderType(processParam.getOrderType());
            saveEntity.setClientSys(processParam.getClientSys());
            saveEntity.setBid(processParam.getBid());
            saveEntity.setDid(processParam.getDid());
            saveEntity.setClientIp(processParam.getClientIp());
            saveEntity.setExpireTime(paymentOrder.getExpireTime());
            saveEntity.setCurrency(processParam.getCurrency());
            saveEntity.setPaymentUrl(paymentOrder.getPaymentUrl());

            boolean res = super.save(saveEntity);

            if (!res) {
                throw new RuntimeException(String.format("%s save CalorieChargeOrder error. data: %s", cm, JsonUtils.logJson(saveEntity)));
            }
            return;
        }
        throw new RuntimeException(String.format("%s optional type miss match. except:%s but it is %s", cm, ProcessParam.class.getName(), optional.getClass()
                .getName()));

    }
}
