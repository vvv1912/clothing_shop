package com.shop.clothing.payment.strategy;

import com.shop.clothing.payment.dto.CreatePaymentResponse;

public interface PaymentStrategy {
    CreatePaymentResponse createPayment(String orderId) throws Exception;
}
