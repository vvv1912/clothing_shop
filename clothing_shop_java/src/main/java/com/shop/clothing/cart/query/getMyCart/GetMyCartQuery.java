package com.shop.clothing.cart.query.getMyCart;

import com.shop.clothing.cart.dto.CartItemDto;
import com.shop.clothing.common.Cqrs.IRequest;

import java.util.Collection;

public record GetMyCartQuery() implements IRequest<Collection<CartItemDto>> {

}
