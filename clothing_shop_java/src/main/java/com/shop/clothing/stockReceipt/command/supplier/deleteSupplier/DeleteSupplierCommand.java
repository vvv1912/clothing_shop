package com.shop.clothing.stockReceipt.command.supplier.deleteSupplier;

import com.shop.clothing.common.Cqrs.IRequest;

public record DeleteSupplierCommand(int supplierId) implements IRequest<Void> {
}
