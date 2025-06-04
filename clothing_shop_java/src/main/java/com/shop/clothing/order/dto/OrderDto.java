package com.shop.clothing.order.dto;

import com.shop.clothing.common.dto.AuditableDto;
import com.shop.clothing.order.entity.enums.OrderStatus;
import com.shop.clothing.payment.dto.PaymentDto;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.promotion.PromotionDto;
import com.shop.clothing.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class OrderDto extends AuditableDto {

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
    private UserDto user;
    private LocalDateTime completedDate;
    public String getCompletedDateDisplay() {
        return completedDate != null ? completedDate.format(DateTimeFormatter.ofPattern(" HH:mm dd/MM/yyyy")) : "";
    }

    private java.util.List<OrderItemDto> orderItems;
    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItemDto::getTotalPrice).sum();
    }
    public int getReducePrice() {
        return (int) (getTotalPrice() + deliveryFee - totalAmount);
    }
}
