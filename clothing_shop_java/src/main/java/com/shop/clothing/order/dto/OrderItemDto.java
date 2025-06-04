package com.shop.clothing.order.dto;

import com.shop.clothing.product.dto.ProductOptionDetailDto;
import com.shop.clothing.product.entity.ProductOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemDto {
    private String orderId;
    private int productOptionId;
    private int quantity;
    private double price;
    private ProductOptionDetailDto productOption;

    public int getTotalPrice() {
        return (int) (quantity * price);
    }
}
