package com.shop.clothing.payment.momo;

import com.shop.clothing.config.AppProperties;
import com.shop.clothing.config.RestExceptionHandler;
import com.shop.clothing.payment.entity.Payment;
import com.shop.clothing.payment.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.UUID;


@AllArgsConstructor
@Service
public class MomoService {
    private final MomoConfig momoConfig;
    private final AppProperties appProperties;
    private final Logger logger = LoggerFactory.getLogger(MomoService.class);
    private final com.shop.clothing.payment.repository.PaymentRepository paymentRepository;


    public MomoCreatePaymentResponse createQRCodePayment(String paymentId, int amount, String orderInfo) throws Exception {
        return createPayment(paymentId, amount, orderInfo, RequestType.QR_CODE);
    }

    public MomoCreatePaymentResponse createATMPayment(String paymentId, int amount, String orderInfo) throws Exception {
        return createPayment(paymentId, amount, orderInfo, RequestType.PAY_WITH_ATM);
    }

    public MomoCreatePaymentResponse createPayment(String paymentId, int amount, String orderInfo, RequestType requestType) throws Exception {

        String partnerCode = momoConfig.getPartnerCode();
        String accessKey = momoConfig.getAccessKey();
        String secretKey = momoConfig.getSecretKey();
        var extraData = "Thanh toán đơn hàng";
        var orderId = UUID.randomUUID().toString();
        String sb = "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + appProperties.getHost() + momoConfig.getIpnUrl() +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + appProperties.getHost() + momoConfig.getCallbackUrl() +
                "&requestId=" + paymentId +
                "&requestType=" + requestType.toString();
        var signature = signHmacSHA256(sb, secretKey);

        JSONObject body = new JSONObject();
        body.put("partnerCode", partnerCode);
        body.put("partnerName", "Ngô Hữu Hoàng");
        body.put("storeId", "CUAHANG_QUANAO");
        body.put("requestId", paymentId);
        body.put("amount", amount);
        body.put("orderId", orderId);
        body.put("orderInfo", orderInfo);
        body.put("redirectUrl", appProperties.getHost() + momoConfig.getCallbackUrl());
        body.put("ipnUrl", appProperties.getHost() + momoConfig.getIpnUrl());
        body.put("lang", "vi");
        body.put("extraData", extraData);
        body.put("requestType", requestType.toString());
        body.put("signature", signature);
        String url = "https://test-payment.momo.vn/v2/gateway/api/create";
        var restTemplate = new org.springframework.web.client.RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestParams = new HttpEntity<>(body.toString(), requestHeaders);
        ResponseEntity<MomoCreatePaymentResponse> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.POST,
                requestParams,
                MomoCreatePaymentResponse.class
        );
        return response.getBody();
    }

    private String signHmacSHA256(String data, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return toHexString(rawHmac);
    }

    private String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return sb.toString();
    }


    public JSONObject checkPaymentStatus(String paymentId, String orderId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String partnerCode = momoConfig.getPartnerCode();
        String accessKey = momoConfig.getAccessKey();
        String secretKey = momoConfig.getSecretKey();
        String sb = "accessKey=" + accessKey +
                "&orderId=" + orderId +
                "&partnerCode=" + partnerCode +
                "&requestId=" + paymentId;
        var signature = signHmacSHA256(sb, secretKey);

        JSONObject body = new JSONObject();
        body.put("partnerCode", partnerCode);
        body.put("requestId", paymentId);
        body.put("orderId", orderId);
        body.put("signature", signature);
        body.put("lang", "vi");
        String url = "https://test-payment.momo.vn/v2/gateway/api/query";
        var restTemplate = new org.springframework.web.client.RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestParams = new HttpEntity<>(body.toString(), requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.POST,
                requestParams,
                String.class
        );
        return new JSONObject(response.getBody());
    }

    public Payment handleCallback(MomoCallbackParam momoCallbackParam) {
        var payment = paymentRepository.findById(momoCallbackParam.getRequestId()).orElseThrow();
        if (payment.getCompletedDate() != null) {
            return payment;
        }
//        var signature = momoCallbackParam.getSignature();// toDo: check signature
        //Chữ ký để kiểm tra tính đúng đắn của dữ liệu khi truyền tải trên mạng. Sử dụng thuật toán Hmac_SHA256 với data theo định dạng được sort từ a-z :
        //accessKey=$accessKey&amount=$amount&extraData=$extraData
        //&message=$message&orderId=$orderId&orderInfo=$orderInfo
        //&orderType=$orderType&partnerCode=$partnerCode
        //&payType=$payType&requestId=$requestId&responseTime=$responseTime
        //&resultCode=$resultCode&transId=$transId
//        var accessKey = momoConfig.getAccessKey();
//        var amount = momoCallbackParam.getAmount();
//        var extraData = momoCallbackParam.getExtraData();
//        var message = momoCallbackParam.getMessage();
//        var orderId = momoCallbackParam.getOrderId();
//        var orderInfo = momoCallbackParam.getOrderInfo();
//        var orderType = momoCallbackParam.getOrderType();
//        var partnerCode = momoCallbackParam.getPartnerCode();
//        var payType = momoCallbackParam.getPayType();
//        var requestId = momoCallbackParam.getRequestId();
//        var responseTime = momoCallbackParam.getResponseTime();
//        var resultCode = momoCallbackParam.getResultCode();
//        var transId = momoCallbackParam.getTransId();
//        var sb = "accessKey=" + accessKey +
//                "&amount=" + amount +
//                "&extraData=" + extraData +
//                "&message=" + message +
//                "&orderId=" + orderId +
//                "&orderInfo=" + orderInfo +
//                "&orderType=" + orderType +
//                "&partnerCode=" + partnerCode +
//                "&payType=" + payType +
//                "&requestId=" + requestId +
//                "&responseTime=" + responseTime +
//                "&resultCode=" + resultCode +
//                "&transId=" + transId;
//        try {
//            var signature2 = signHmacSHA256(sb, momoConfig.getSecretKey());
//            if (!signature.equals(signature2)) {
//                throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Giao dịch không hợp lệ");
//            }
//        } catch (Exception e) {
//            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Giao dịch không hợp lệ");
//        }
        JSONObject jsonObject = null;
        try {
            jsonObject = this.checkPaymentStatus(momoCallbackParam.getRequestId(), momoCallbackParam.getOrderId());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Giao dịch không hợp lệ");
        }
        switch (jsonObject.getInt("resultCode")) {
            case 0 -> payment.setStatus(PaymentStatus.PAID);
            case 8000, 7000, 1000 -> payment.setStatus(PaymentStatus.PENDING);
            default -> payment.setStatus(PaymentStatus.FAILED);
        }
        if (payment.getStatus() == PaymentStatus.PAID) {
            payment.setCompletedDate(LocalDateTime.now());
        }
        paymentRepository.save(payment);
        return payment;
    }


}
