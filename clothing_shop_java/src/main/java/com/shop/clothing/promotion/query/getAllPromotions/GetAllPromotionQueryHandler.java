package com.shop.clothing.promotion.query.getAllPromotions;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.promotion.PromotionDto;
import com.shop.clothing.promotion.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class GetAllPromotionQueryHandler implements IRequestHandler<GetAllPromotionQuery, Paginated<PromotionDto>> {
    private final PromotionRepository promotionRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public HandleResponse<Paginated<PromotionDto>> handle(GetAllPromotionQuery getAllPromotionQuery) {
        var result = promotionRepository.search(
                getAllPromotionQuery.getKeyword(),
                getAllPromotionQuery.getFromDate(),
                getAllPromotionQuery.getToDate(),
                getAllPromotionQuery.getPageable("promotionId")
        );
        var promotionDtos = result.map(promotion -> modelMapper.map(promotion, PromotionDto.class));
        return HandleResponse.ok(Paginated.of(promotionDtos));
    }
}
