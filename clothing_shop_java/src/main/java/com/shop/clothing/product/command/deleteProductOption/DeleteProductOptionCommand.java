package com.shop.clothing.product.command.deleteProductOption;

import com.shop.clothing.common.Cqrs.IRequest;

public record DeleteProductOptionCommand(int id)  implements IRequest<Void> {
}
