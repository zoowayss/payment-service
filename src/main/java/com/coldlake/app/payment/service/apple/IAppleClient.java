package com.coldlake.app.payment.service.apple;

import com.coldlake.app.payment.domain.apple.AppleQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/26 16:22
 */
@Component
@Slf4j
public class IAppleClient {
    /**
     * 沙盒环境apple支付收据验证接口
     */
    private static final String VERIFY_RECEIPT_SANDBOX_URL = "https://sandbox.itunes.apple.com/verifyReceipt";

    /**
     * 生产环境apple支付收据验证接口
     */
    private static final String VERIFY_RECEIPT_PRODUCTION_URL = "https://buy.itunes.apple.com/verifyReceipt";

    /**
     * apple支付共享密钥
     */
    private static final String SHARE_SECRET = "440df02fd72349c4adf769e3b1f66d04";


    /**
     * apple 支付的bundle id
     */
    public static final String BUNDLE_ID = "cn.coldlake.beakerm";

    /**
     * apple支付验证收据状态码，表示收据信息是沙盒环境的，但却被发送到产品环境中验证
     */
    private static final int SANDBOX_CODE = 21007;
    @Resource
    private RestTemplate restTemplate;


    public AppleQueryResult doSuccessPay(String uid, String productId, String receiptData, String transactionId, String mode) throws Exception {
//        String cm = "doSuccessPay@IAppleClient";
//        String url = APPLE_SANDBOX.equalsIgnoreCase(mode) ? VERIFY_RECEIPT_SANDBOX_URL : VERIFY_RECEIPT_PRODUCTION_URL;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        JSONObject object = new JSONObject();
//        object.put("receipt-data", receiptData);
//        object.put("password", SHARE_SECRET);
//        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(object, headers);
//
//
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
//        Optional.ofNullable(responseEntity).orElseThrow(() -> new RuntimeException(String.format("%s body is null, response=%s", cm, responseEntity)));
//
//        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
//            log.error(cm + " status err, response={}", responseEntity);
//            throw new RuntimeException(String.format("status err, response=%s", responseEntity));
//        }
//
//        Optional.ofNullable(responseEntity.getBody()).orElseThrow(() -> new RuntimeException(String.format("%s body is null, response=%s", cm, responseEntity)));
//
//
//        AppleQueryResult response = JSON.parseObject(responseEntity.getBody(), AppleQueryResult.class);
//        if (response.getStatus() == SANDBOX_CODE) {
//            return doSuccessPay(uid, productId, receiptData, transactionId, APPLE_SANDBOX);
//        }
//        if (response.getStatus() != 0 || response.getReceipt() == null) {
//            log.error("trade_no:{},苹果服务器获取数据异常:{}", transactionId, response);
//            throw new RuntimeException(String.format("苹果服务器获取数据异常: tradeNo:%s  response:%s", transactionId, response));
//        }
//        if (CollectionUtils.isEmpty(response.getReceipt().getIn_app())) {
//            log.error("trade_no:{},apple in_app数据为空,result:{}", transactionId, response);
//            throw new RuntimeException(String.format("apple in_app数据为空,tradeNo:%s result: %s", transactionId, response));
//        }
//
//        if (!BUNDLE_ID.equals(response.getReceipt().getBundle_id())) {
//            log.error("trade_no:{},bundle_id不匹配,result:{}", transactionId, response);
//            throw new RuntimeException(String.format("bundle_id不匹配,tradeNo:%s result: %s", transactionId, response));
//        }
//        response.getReceipt().getIn_app().forEach(x -> {
//            if (x.getTransaction_id().equals(transactionId)) {
//                response.setApp(x);
//            }
//        });
//        log.info("{} get order, uid:{}, productId:{}, data:{},transactionId:{}", cm, uid, productId, JSON.toJSONString(response), transactionId);
//        return response;
        return null;
    }
}
