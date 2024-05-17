package com.coldlake.app.payment.service.apple;

import com.coldlake.app.payment.domain.AppRetrieveOrderWrapper;
import com.coldlake.app.payment.domain.PaymentOrder;
import com.coldlake.app.payment.domain.apple.AppleQueryResult;
import com.coldlake.app.payment.domain.constants.Constants;
import com.coldlake.app.payment.service.AbstractAppPaymentHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 苹果一次性订单支付处理器
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 11:17
 */
@Service
public abstract class AbstractApplePaymentOrderHandler extends AbstractAppPaymentHandler {

    public static final String NAME = "ABSTRACT_APPLE_PAYMENT_ORDER";
    //apple支付沙盒环境标记
    public static final String APPLE_SANDBOX = "Sandbox";
    //apple支付生产环境标记
    public static final String APPLE_PRODUCTION = "Production";




    @Resource
    private IAppleClient appleClient;

    @Override
    public PaymentOrder successPay(AppRetrieveOrderWrapper orderWrapper) throws Exception {
        String cm = "getAppleOrderInfo@PaymentServiceImpl";

        AppleQueryResult appleQueryResult = appleClient.doSuccessPay(orderWrapper.getUid(), orderWrapper.getProductId(), orderWrapper.getReceiptData(), orderWrapper.getTransactionId(), orderWrapper.getMod());

        PaymentOrder res = new PaymentOrder();
        res.setTradeNo(appleQueryResult.getApp().getOriginal_transaction_id());
        res.setLastPaymentTime(Long.parseLong(appleQueryResult.getApp().getPurchase_date_ms()));

        return res;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getSuccessUri() {
        return Constants.APPLE_SUCCESS_URI;
    }

    @Override
    public String getFailureUri() {
        return Constants.APPLE_FAILED_URI;
    }

}
