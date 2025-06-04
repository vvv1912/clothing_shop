package com.shop.clothing.promotion.command.tryDeletePromotion;

import com.shop.clothing.common.Cqrs.IRequest;

public record TryDeletePromotionCommand (int promotionId) implements IRequest<Void> {
}
