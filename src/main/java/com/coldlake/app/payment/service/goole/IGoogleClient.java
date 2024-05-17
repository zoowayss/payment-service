package com.coldlake.app.payment.service.goole;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/28 09:52
 */
@Component
@Slf4j
public class IGoogleClient {
    @Value(value = "classpath:aichatgpayprivate.yml")
    private Resource resource;

    @Value("${payment.google.packageName}")
    private String packageName;

    @Value("${payment.google.proxyHost}")
    private String proxyHost;

    @Value("${payment.google.proxyPort}")
    private Integer proxyPort;

    @Value(value = "${payment.google.proxySw}")
    private Boolean proxySw;

    @Value(value = "${payment.google.aichatAPPName:Crushmate}")
    private String aichatAPPName;

//    private static HttpTransport getHttpTransport(Boolean isProxy, String proxyHost, Integer proxyPort) throws IOException, GeneralSecurityException {
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
//        return !isProxy ? GoogleNetHttpTransport.newTrustedTransport() : new NetHttpTransport.Builder()
//                .trustCertificates(GoogleUtils.getCertificateTrustStore())
//                .setProxy(proxy) //设置代理
//                .build();
//    }
//
//    private AndroidPublisher getAndroidPublisher() throws IOException, GeneralSecurityException {
//        HttpTransport httpTransport = getHttpTransport(proxySw, proxyHost, proxyPort);
//        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
//        // 凭证
//        GoogleCredential credentials = GoogleCredential.fromStream(resource.getInputStream(),
//                        httpTransport, jsonFactory)
//                .createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER));
//
//        return new AndroidPublisher.Builder(
//                httpTransport,
//                jsonFactory,
//                credentials).setApplicationName(aichatAPPName).build();
//    }
//
//
//    public ProductPurchase getGoogleOrderInfo(String orderId, String productId, String purchaseToken) {
//        String cm = "getGoogleOrderInfo@IGoogleClient";
//        try {
//            AndroidPublisher androidPublisher = getAndroidPublisher();
//            ProductPurchase res = Optional.ofNullable(androidPublisher.purchases().products().get(packageName, productId, purchaseToken)
//                    .execute()).orElseThrow(() -> new RuntimeException(String.format("payment authentication failed. orderId:%s, productId:%s, purchaseToken:%s", orderId, productId, purchaseToken)));
//            log.info("{} get order, orderId:{}, productId:{}, data:{}", cm, orderId, productId, JSON.toJSONString(res));
//            return res;
//        } catch (Exception e) {
//            log.error("{} err, orderId:{}, productId:{}, error:", cm, orderId, productId, e);
//            throw new RuntimeException("payment failed", e);
//        }
//    }
//
//    public void acknowledgeProducts(String orderId, String productId, String purchaseToken) {
//        String cm = "acknowledgeProducts@IGoogleClient";
//        try {
//            AndroidPublisher androidPublisher = getAndroidPublisher();
//            ProductPurchasesAcknowledgeRequest request = new ProductPurchasesAcknowledgeRequest();
//            request.setDeveloperPayload("");
//            androidPublisher.purchases().products().acknowledge(packageName, productId, purchaseToken, request)
//                    .execute();
//            log.info("{} finish, orderId:{}, productId:{}, token:{}", cm, orderId, productId, purchaseToken);
//
//        } catch (Exception e) {
//            log.error("{} err, orderId:{}, productId:{}, token:{}, error:", cm, orderId, productId, purchaseToken, e);
//            throw new RuntimeException("acknowledge failed", e);
//        }
//    }
//
//
//    public SubscriptionPurchase getGoogleSubscription(String orderId, String productId, String purchaseToken) {
//        String cm = "getGoogleSubscription@IGoogleClient";
//        try {
//            AndroidPublisher androidPublisher = getAndroidPublisher();
//
//            SubscriptionPurchase res = Optional.ofNullable(androidPublisher.purchases().subscriptions().get(packageName, productId, purchaseToken)
//                    .execute()).orElseThrow(() -> new RuntimeException(String.format("payment authentication failed. orderId:%s, productId:%s, purchaseToken:%s", orderId, productId, purchaseToken)));
//            log.info("{} get order, orderId:{}, productId:{}, data:{}", cm, orderId, productId, JSON.toJSONString(res));
//            return res;
//        } catch (Exception e) {
//            log.error("{} err, orderId:{}, productId:{}, error:", cm, orderId, productId, e);
//            throw new RuntimeException("payment failed");
//        }
//    }
//
//    public void acknowledgeSubscription(String orderId, String productId, String purchaseToken) {
//        String cm = "acknowledgeSubscription@IGoogleClient";
//        try {
//            AndroidPublisher androidPublisher = getAndroidPublisher();
//            SubscriptionPurchasesAcknowledgeRequest request = new SubscriptionPurchasesAcknowledgeRequest();
//            request.setDeveloperPayload("");
//            androidPublisher.purchases().subscriptions().acknowledge(packageName, productId, purchaseToken, request)
//                    .execute();
//            log.info("{} finish, orderId:{}, productId:{}, token:{}", cm, orderId, productId, purchaseToken);
//
//        } catch (Exception e) {
//            log.error("{} err, orderId:{}, productId:{}, token:{}, error:", cm, orderId, productId, purchaseToken, e);
//            throw new RuntimeException("acknowledge failed");
//        }
//    }
}
