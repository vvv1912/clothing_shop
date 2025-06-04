package com.shop.clothing.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDetailDto extends ProductBriefDto {

    private java.util.List<ProductOptionDto> productOptions;
    private java.util.List<ProductImageDto>images;
    private String description;

}
