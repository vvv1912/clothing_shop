package com.shop.clothing.stockReceipt.query.getById;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.stockReceipt.dto.StockReceiptDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public record GetStockReceiptByIdQuery(int id) implements IRequest<StockReceiptDetailDto> {

}
