package com.shop.clothing.promotion.query.checkPromotion;

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
public class CheckPromotionQueryHandler implements IRequestHandler<CheckPromotionQuery, PromotionDto> {
    private final PromotionRepository promotionRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional(readOnly = true)
    public HandleResponse<PromotionDto> handle(CheckPromotionQuery checkPromotionQuery) throws Exception {
        var promotionOptional = promotionRepository.findByCodeIgnoreCase(checkPromotionQuery.getCode());
        if (promotionOptional.isEmpty()) {
            return HandleResponse.error("Mã giảm giá không hợp lệ", HttpStatus.NOT_FOUND);
        }
        var today = new java.sql.Date(System.currentTimeMillis());

        var promotion = promotionOptional.get();
        if (!promotion.isActive()||promotion.getStartDate().after(today)) {
            return HandleResponse.error("Mã giảm giá không hợp lệ", HttpStatus.NOT_FOUND);
        }
        if (promotion.getMinOrderAmount() > checkPromotionQuery.getOrderValue()) {
            return HandleResponse.error("Chưa đạt giá trị đơn hàng tối thiểu (" + promotion.getMinOrderAmount() + ")", HttpStatus.NOT_FOUND);
        }

        if(promotion.getEndDate().before(today)){
            return HandleResponse.error("Mã giảm giá đã hết hạn", HttpStatus.BAD_REQUEST);
        }
        if (promotion.getStock() <= 0) {
            return HandleResponse.error("Mã giảm giá đã hết", HttpStatus.NOT_FOUND);
        }
        var promotionDto = modelMapper.map(promotion, PromotionDto.class);
        return HandleResponse.ok(promotionDto);



    }
}
