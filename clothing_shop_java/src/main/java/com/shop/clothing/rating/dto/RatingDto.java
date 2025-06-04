package com.shop.clothing.rating.dto;

import com.shop.clothing.common.dto.AuditableDto;
import com.shop.clothing.product.dto.ProductOptionDto;
import com.shop.clothing.user.UserBriefDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto extends AuditableDto {
    private int id;
    private String content;
    private int value;
    private UserBriefDto user;
    private ProductOptionDto productOption;
}
