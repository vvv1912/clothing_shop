package com.shop.clothing.product.dto;

import com.shop.clothing.product.entity.Color;
import com.shop.clothing.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductImageDto {
    private String url;
    private ColorDto forColor;
}
