package com.shop.clothing.payment.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.clothing.common.BusinessLogicException;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.payment.dto.CreatePaymentResponse;
import com.shop.clothing.payment.entity.Payment;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.payment.entity.enums.PaymentStatus;
import com.shop.clothing.payment.momo.MomoCreatePaymentResponse;
import com.shop.clothing.payment.momo.MomoService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MomoQrPaymentStrategy implements PaymentStrategy {
    private final MomoService momoService;
    private final OrderRepository orderRepository;
    private final com.shop.clothing.payment.repository.PaymentRepository paymentRepository;

    @Override
    @Transactional
    public CreatePaymentResponse createPayment(String orderId) throws Exception {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException( HttpStatus.BAD_REQUEST,"Đơn hàng không tồn tại"));

        var lastPayment = paymentRepository.findFirstByOrderIdSortedByCreatedDateDesc(orderId).orElse(null);
        var paymentId = UUID.randomUUID().toString();
        if (lastPayment != null && lastPayment.getStatus() == PaymentStatus.PENDING) {
            paymentId = lastPayment.getPaymentId();
        }
        var response = momoService.createQRCodePayment(paymentId, order.getTotalAmount(), "Thanh toán đơn hàng " + orderId);
        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(response);
        if (lastPayment == null || lastPayment.getStatus() != PaymentStatus.PENDING) {
            var payment = Payment.builder().paymentId(paymentId)
                    .amount(order.getTotalAmount())
                    .order(order)
                    .build();
            paymentRepository.save(payment);
        }

        return CreatePaymentResponse.builder()
                .paymentId(response.getRequestId())
                .isRedirect(true)
                .paymentMethod(PaymentMethod.MOMO_QR)
                .mobileUrl(response.getDeeplink())
                .redirectUrl(response.getPayUrl())
                .build();
    }
}
