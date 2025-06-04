package com.shop.clothing.product.command.deleteProduct;

import com.shop.clothing.common.Cqrs.IRequest;

public record DeleteProductCommand(int id) implements IRequest<Void> {
}
