package com.shop.clothing.product.command.recoveryProductOption;

import com.shop.clothing.common.Cqrs.IRequest;

public record RecoveryProductOptionCommand(int productOptionId) implements IRequest<Void> {
}
