package com.shop.clothing.promotion.query.getPromotionById;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.promotion.PromotionDto;
import com.shop.clothing.promotion.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class GetPromotionByIdQueryHandler implements IRequestHandler<GetPromotionByIdQuery, PromotionDto> {
    private final PromotionRepository promotionRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional(readOnly = true)
    public HandleResponse<PromotionDto> handle(GetPromotionByIdQuery query) throws Exception {
        var promotion = promotionRepository.findById(query.getPromotionId());
        if (promotion.isEmpty()) {
            return HandleResponse.error("Không tìm thấy mã giảm giá");
        }
        var promotionDto = modelMapper.map(promotion.get(), PromotionDto.class);
        return HandleResponse.ok(promotionDto);



    }
}
