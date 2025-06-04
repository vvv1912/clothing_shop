package com.shop.clothing.promotion.command.toggleDisablePromotion;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.promotion.command.tryDeletePromotion.TryDeletePromotionCommand;
import com.shop.clothing.promotion.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ToggleDisablePromotionCommandHandler implements IRequestHandler<ToggleDisablePromotion, Void> {
    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(ToggleDisablePromotion command) throws Exception {
        var promotion = promotionRepository.findById(command.promotionId());
        if (promotion.isEmpty()) {
            return HandleResponse.error("Không tìm thấy mã giảm giá");
        }
        promotion.get().setActive(!promotion.get().isActive());
        return HandleResponse.ok();
    }
}
