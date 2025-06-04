package com.shop.clothing.product.dto;

import com.shop.clothing.product.entity.ProductImage;
import com.shop.clothing.product.entity.ProductOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ColorDto {
    private int colorId;
    private String name;
    private String image;
}
