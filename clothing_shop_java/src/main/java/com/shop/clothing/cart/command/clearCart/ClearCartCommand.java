package com.shop.clothing.cart.command.clearCart;

import com.shop.clothing.common.Cqrs.IRequest;

public record ClearCartCommand() implements IRequest<Integer> {
}
