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
import com.shop.clothing.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MomoATMPaymentStrategy implements PaymentStrategy {
    private final MomoService momoService;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public CreatePaymentResponse createPayment(String orderId) throws Exception {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessLogicException("Đơn hàng không tồn tại"));
        var paymentId = UUID.randomUUID().toString();
        var lastPayment = paymentRepository.findFirstByOrderIdSortedByCreatedDateDesc(orderId).orElse(null);
        if(lastPayment != null && lastPayment.getStatus() != PaymentStatus.FAILED&&  lastPayment.getStatus() != PaymentStatus.CANCELLED){
            paymentId = lastPayment.getPaymentId();
        }
        var response = momoService.createATMPayment(paymentId, order.getTotalAmount(), "Thanh toán đơn hàng " + orderId);

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
                .paymentId(paymentId)
                .isRedirect(true)
                .paymentMethod(PaymentMethod.MOMO_QR)
                .mobileUrl(response.getDeeplink())
                .redirectUrl(response.getPayUrl())
                .build();
    }
}
