package com.coldlake.app.payment.domain;

import com.coldlake.app.payment.domain.constants.Constants;
import com.coldlake.app.payment.domain.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.domain.enums.PaymentTypeEnum;
import com.coldlake.app.payment.domain.paypal.subscription.Link;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class PaymentOrder {


    private String tradeNo;

    private PaymentTypeEnum payType;

    private String subscribeId;

    private PaymentOrderStatusEnum status;

    private PaymentOrderStatusEnum subscribeStatus;

    private String paymentUrl;

    private Long expireTime;

    private Long LastPaymentTime;

    private Long uid;

    private String lastInvoiceId;


    public PaymentOrder(String tradeNo, PaymentTypeEnum payType, String subscribeId, PaymentOrderStatusEnum status, PaymentOrderStatusEnum subscribeStatus, String paymentUrl, Long expireTime, Long lastPaymentTime, Long uid, String lastInvoiceId) {
        this.tradeNo = tradeNo;
        this.payType = payType;
        this.subscribeId = subscribeId;
        this.status = status;
        this.subscribeStatus = subscribeStatus;
        this.paymentUrl = paymentUrl;
        this.expireTime = expireTime;
        LastPaymentTime = lastPaymentTime;
        this.uid = uid;
        this.lastInvoiceId = lastInvoiceId;
    }


    public PaymentOrder(String tradeNo, PaymentTypeEnum payType, String subscribeId, Long lastPaymentTime, PaymentOrderStatusEnum status, String key, List<Link> links) {
        this(tradeNo, payType, subscribeId, status, key, links);
        this.LastPaymentTime = lastPaymentTime;
    }

    public PaymentOrder(String tradeNo, PaymentTypeEnum payType, String subscribeId, Long lastPaymentTime, PaymentOrderStatusEnum subscribeStatus, PaymentOrderStatusEnum status, String key, List<Link> links) {
        this(tradeNo, payType, subscribeId, status, key, links);
        this.LastPaymentTime = lastPaymentTime;
        this.subscribeStatus = subscribeStatus;
    }


    public PaymentOrder(String tradeNo, PaymentTypeEnum payType, String subscribeId, PaymentOrderStatusEnum status, String key, List<Link> links) {
        this.tradeNo = tradeNo;
        this.payType = payType;
        this.subscribeId = subscribeId;
        this.status = status;
        this.paymentUrl = links.stream().filter(l -> key.equals(l.getRel())).findFirst().orElse(Link.EMPTY).getHref();
    }

    public PaymentOrder(String tradeNo, PaymentTypeEnum payType, String subscribeId, PaymentOrderStatusEnum status, String key, List<Link> links, Long expireTime) {
        this(tradeNo, payType, subscribeId, status, key, links);
        this.expireTime = expireTime;
    }

    public PaymentOrder(String tradeNo, PaymentTypeEnum payType, String subscribeId, PaymentOrderStatusEnum status, String paymentUrl, Long expireTime) {
        this(tradeNo, payType, subscribeId, status, null, paymentUrl, expireTime, null, null, "");
    }



    public static PaymentOrder newByStripeCheckoutSession(String tradeNo, PaymentTypeEnum payType, String subscribeId, PaymentOrderStatusEnum status, String paymentUrl, Long expireTime) {
        return new PaymentOrder(tradeNo, payType, subscribeId, status, null, paymentUrl, expireTime * Constants.ONE_SECOND_MILL, null, null, "");
    }

    public static PaymentOrder newByStripeSubscription(String subscribeId, PaymentTypeEnum payType, Long lastPaymentTime, PaymentOrderStatusEnum status, PaymentOrderStatusEnum subscribeStatus, String lastInvoiceId) {
        return new PaymentOrder("", payType, subscribeId, status, subscribeStatus, "", null, lastPaymentTime * Constants.ONE_SECOND_MILL, null, lastInvoiceId);
    }
}




