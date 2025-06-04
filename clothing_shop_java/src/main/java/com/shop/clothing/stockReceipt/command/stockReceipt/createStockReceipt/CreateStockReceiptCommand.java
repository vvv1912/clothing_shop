package com.shop.clothing.stockReceipt.command.stockReceipt.createStockReceipt;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.stockReceipt.entity.StockReceiptItem;
import com.shop.clothing.stockReceipt.entity.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStockReceiptCommand implements IRequest<Integer> {
    public static class CreateItemCommand {
        @Min(value = 1, message = "Yêu cầu chọn sản phẩm")
        public int productOptionId;
        @Min(value = 1, message = "Yêu cầu nhập số lượng sản phẩm")
        public int quantity;
        @Min(value = 0, message = "Yêu cầu nhập giá sản phẩm")
        public int price;
    }


    private String note;
    @Min(value = 1, message = "Yêu cầu chọn nhà cung cấp")
    private int supplierId;
    @Size(min = 1, message = "Yêu cầu chọn ít nhất 1 sản phẩm để nhập kho")
    private java.util.List<CreateItemCommand> stockReceiptItems;
}
