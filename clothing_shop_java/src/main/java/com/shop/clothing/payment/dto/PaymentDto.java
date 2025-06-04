package com.shop.clothing.payment.dto;

import com.shop.clothing.payment.entity.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentDto {
    private String paymentId;
    private PaymentStatus status = PaymentStatus.PENDING;

    private String paymentDetails;
    private String paymentResponse;
    private float amount;
}
