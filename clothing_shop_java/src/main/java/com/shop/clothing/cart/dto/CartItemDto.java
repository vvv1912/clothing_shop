package com.shop.clothing.cart.dto;

import com.shop.clothing.product.dto.ProductOptionDetailDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private String userId;
    private int productOptionId;
    private int quantity;
    private ProductOptionDetailDto productOption;
}
