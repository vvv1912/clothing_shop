package com.shop.clothing.stockReceipt.command.stockReceipt.deleteStockReceipt;

import com.shop.clothing.common.Cqrs.IRequest;

public record DeleteStockReceiptCommand (int stockReceiptId) implements IRequest<Void> {
}
