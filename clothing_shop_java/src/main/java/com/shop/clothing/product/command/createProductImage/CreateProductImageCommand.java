package com.shop.clothing.product.command.createProductImage;

import com.shop.clothing.common.Cqrs.IRequest;

public record CreateProductImageCommand(int productId, int colorId,String url) implements IRequest<Void> {
}
