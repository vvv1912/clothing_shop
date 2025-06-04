package com.shop.clothing.order.dto;

import com.shop.clothing.common.dto.AuditableDto;
import com.shop.clothing.order.entity.enums.OrderStatus;
import com.shop.clothing.payment.dto.PaymentDto;
import com.shop.clothing.payment.entity.Payment;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.promotion.PromotionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderDetailDto extends OrderDto{
    private List<PaymentDto> payments;
}
