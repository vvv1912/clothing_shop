package com.shop.clothing.promotion.query.getPromotionById;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.promotion.PromotionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPromotionByIdQuery implements IRequest<PromotionDto> {
    private int promotionId;
}
