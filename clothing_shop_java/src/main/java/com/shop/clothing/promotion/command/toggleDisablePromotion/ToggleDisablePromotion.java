package com.shop.clothing.promotion.command.toggleDisablePromotion;

import com.shop.clothing.common.Cqrs.IRequest;

public record ToggleDisablePromotion(int promotionId) implements IRequest<Void> {
}
