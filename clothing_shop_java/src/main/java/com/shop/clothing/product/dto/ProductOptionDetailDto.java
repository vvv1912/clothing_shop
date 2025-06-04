package com.shop.clothing.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDetailDto extends ProductOptionDto {
    private ProductBriefDto product;
    private int quantity=0;

    public int getFinalPrice() {
        return (int) (this.getProduct().getPrice() * (100.0 - this.getProduct().getDiscount()) / 100);
    }

    public String getFinalPriceDisplay() {
        return String.format("%,dđ", getFinalPrice());
    }
}
