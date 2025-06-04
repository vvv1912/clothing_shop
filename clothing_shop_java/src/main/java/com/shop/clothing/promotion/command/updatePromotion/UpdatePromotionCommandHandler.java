package com.shop.clothing.promotion.command.updatePromotion;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.promotion.Promotion;
import com.shop.clothing.promotion.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class UpdatePromotionCommandHandler implements IRequestHandler<UpdatePromotionCommand, Integer> {
    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public HandleResponse<Integer> handle(UpdatePromotionCommand command) throws Exception {
        var exist = promotionRepository.findByCodeIgnoreCase(command.getCode());
        if (exist.isPresent()&&exist.get().getPromotionId()!=command.getPromotionId()) {
            return HandleResponse.error("Code " + command.getCode() + " đã tồn tại");
        }
        var promotion = exist.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy mã giảm giá"));
        promotion.setActive(command.isActive());
        promotion.setCode(command.getCode());
        promotion.setEndDate(command.getEndDate());
        promotion.setDescription(command.getDescription());
        promotion.setName(command.getName());
        promotion.setStock(command.getStock());
        promotion.setStartDate(command.getStartDate());
        promotion.setDiscount(command.getDiscount());
        promotion.setMaxValue(command.getMaxValue());
        promotion.setMinOrderAmount(command.getMinOrderAmount());
        promotion.setType(command.getType());
        promotionRepository.save(promotion);
        return HandleResponse.ok();

    }
}
