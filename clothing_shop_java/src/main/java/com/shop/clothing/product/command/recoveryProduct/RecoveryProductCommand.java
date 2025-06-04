package com.shop.clothing.product.command.recoveryProduct;

import com.shop.clothing.common.Cqrs.IRequest;

public record RecoveryProductCommand(int product) implements IRequest<Void> {
}
