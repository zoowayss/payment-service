package com.coldlake.app.payment.domain.payment;

import com.coldlake.app.payment.domain.payment.constants.Constants;
import com.coldlake.app.payment.domain.payment.enums.PaymentOrderStatusEnum;
import com.coldlake.app.payment.domain.payment.enums.PaymentTypeEnum;
import com.coldlake.app.payment.domain.payment.paypal.orders.enums.ThirdPartOrderStatusAdapterEnum;
import com.coldlake.app.payment.domain.payment.paypal.subscription.Link;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class PaymentOrder {

    /**
     * 第三方交易号
     */
    private String tradeNo;

    /**
     * 支付类型
     * <p>
     * PayPal 一次性支付；PayPal 订阅；Stripe 一次性支付；Stripe 订阅...
     */
    private PaymentTypeEnum payType;

    /**
     * 订阅id
     */
    private String subscribeId;

    /**
     * 订单状态
     */
    private PaymentOrderStatusEnum status;

    /**
     * 订阅状态
     */
    private PaymentOrderStatusEnum subscribeStatus;

    /**
     * 支付链接
     */
    private String paymentUrl;

    /**
     * 订单失效时间 单位： 毫秒
     */
    private Long expireTime;

    /**
     * 如果是订阅类型 订阅上一次支付时间
     */
    private Long LastPaymentTime;


    /**
     * 用户id
     */
    private Long uid;

    /**
     * Stripe 订阅的最后一次账单 id
     */
    private String lastInvoiceId;


    public PaymentOrder(String tradeNo,
                        PaymentTypeEnum payType,
                        String subscribeId,
                        PaymentOrderStatusEnum status,
                        PaymentOrderStatusEnum subscribeStatus,
                        String paymentUrl,
                        Long expireTime,
                        Long lastPaymentTime,
                        Long uid,
                        String lastInvoiceId) {
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


    public PaymentOrder(String tradeNo,
                        PaymentTypeEnum payType,
                        String subscribeId,
                        Long lastPaymentTime,
                        PaymentOrderStatusEnum status,
                        String key,
                        List<Link> links) {
        this(tradeNo, payType, subscribeId, status, key, links);
        this.LastPaymentTime = lastPaymentTime;
    }

    public PaymentOrder(String tradeNo,
                        PaymentTypeEnum payType,
                        String subscribeId,
                        Long lastPaymentTime,
                        PaymentOrderStatusEnum subscribeStatus,
                        PaymentOrderStatusEnum status,
                        String key,
                        List<Link> links) {
        this(tradeNo, payType, subscribeId, status, key, links);
        this.LastPaymentTime = lastPaymentTime;
        this.subscribeStatus = subscribeStatus;
    }


    public PaymentOrder(String tradeNo,
                        PaymentTypeEnum payType,
                        String subscribeId,
                        PaymentOrderStatusEnum status,
                        String key,
                        List<Link> links,
                        Long expireTime) {
        this(tradeNo, payType, subscribeId, status, key, links);
        this.expireTime = expireTime;
    }

    public PaymentOrder(String tradeNo,
                        PaymentTypeEnum payType,
                        String subscribeId,
                        PaymentOrderStatusEnum status,
                        String paymentUrl,
                        Long expireTime) {
        this(tradeNo, payType, subscribeId, status, null, paymentUrl, expireTime, null, null, "");
    }

    public PaymentOrder(String tradeNo, PaymentTypeEnum payType, String subscribeId, PaymentOrderStatusEnum status, String key, List<Link> links) {
        this.tradeNo = tradeNo;
        this.payType = payType;
        this.subscribeId = subscribeId;
        this.status = status;
        this.paymentUrl = links.stream()
                .filter(l -> key.equals(l.getRel()))
                .findFirst()
                .orElse(Link.EMPTY)
                .getHref();
    }

    public static PaymentOrder newPaypalOrder(String tradeNo,
                                              PaymentTypeEnum payType,
                                              String subscribeId,
                                              PaymentOrderStatusEnum status,
                                              String key,
                                              List<Link> links) {
        return new PaymentOrder(tradeNo, payType, subscribeId, status, null, links.stream()
                .filter(l -> key.equals(l.getRel()))
                .findFirst()
                .orElse(Link.EMPTY)
                .getHref(), 0L, 0L, 0L, "");
    }

    public static PaymentOrder newByStripeCheckoutSession(String tradeNo,
                                                          PaymentTypeEnum payType,
                                                          String subscribeId,
                                                          String status,
                                                          String paymentUrl,
                                                          Long expireTime) {
        return new PaymentOrder(tradeNo, payType, subscribeId, ThirdPartOrderStatusAdapterEnum.of(status)
                .convert(), null, paymentUrl, expireTime * Constants.ONE_SECOND_MILL, null, null, "");
    }

    public static PaymentOrder newByStripeSubscription(String subscribeId,
                                                       PaymentTypeEnum payType,
                                                       Long lastPaymentTime,
                                                       String status,
                                                       String subscribeStatus,
                                                       String lastInvoiceId) {
        return new PaymentOrder("", payType, subscribeId, ThirdPartOrderStatusAdapterEnum.of(status)
                .convert(), ThirdPartOrderStatusAdapterEnum.of(subscribeStatus)
                .convert(), "", null, lastPaymentTime * Constants.ONE_SECOND_MILL, null, lastInvoiceId);
    }
}




