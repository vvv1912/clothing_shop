package com.shop.clothing.payment.dto;

import com.shop.clothing.payment.entity.enums.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreatePaymentResponse {
    private String paymentId;
    private PaymentMethod paymentMethod;
    private String orderId;
    private boolean isRedirect;
    private String redirectUrl;
    private String mobileUrl;
}
