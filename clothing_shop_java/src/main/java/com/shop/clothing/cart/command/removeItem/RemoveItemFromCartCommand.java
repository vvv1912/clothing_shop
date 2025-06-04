package com.shop.clothing.cart.command.removeItem;

import com.shop.clothing.common.Cqrs.IRequest;

public record RemoveItemFromCartCommand(int productOptionId) implements IRequest<Void> {
}
