package com.shop.clothing.promotion.command.tryDeletePromotion;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.promotion.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TryDeletePromotionCommandHandler implements IRequestHandler<TryDeletePromotionCommand, Void> {
    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(TryDeletePromotionCommand tryDeletePromotionCommand) throws Exception {
        var promotion = promotionRepository.findById(tryDeletePromotionCommand.promotionId());
        if (promotion.isEmpty()) {
            return HandleResponse.error("Không tìm thấy mã giảm giá");
        }
        if (promotion.get().getOrders().isEmpty()) {
            promotionRepository.delete(promotion.get());
            return HandleResponse.ok();
        }
        return HandleResponse.error("Không thể xóa mã giảm giá đã được sử dụng");
    }
}
