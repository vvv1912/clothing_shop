package com.shop.clothing.order.dto;

import com.shop.clothing.common.dto.AuditableDto;
import com.shop.clothing.order.entity.enums.OrderStatus;
import com.shop.clothing.payment.dto.PaymentDto;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.promotion.PromotionDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderBriefDto extends AuditableDto {
    private String orderId;
    private String customerName;
    private String address;
    private PaymentMethod paymentMethod;
    private String phoneNumber;
    private String email;
    private int totalAmount;
    private String note;
    private double deliveryFee;
    private String cancelReason;
    private OrderStatus status = OrderStatus.PENDING;
    private PaymentDto latestPayment;
    private PromotionDto promotion;
    private LocalDateTime completedDate;
}
