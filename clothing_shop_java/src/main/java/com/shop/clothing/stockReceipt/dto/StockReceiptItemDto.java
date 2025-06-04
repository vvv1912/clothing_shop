package com.shop.clothing.stockReceipt.dto;

import com.shop.clothing.product.dto.ProductOptionDetailDto;
import com.shop.clothing.product.entity.ProductOption;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockReceiptItemDto {
    private int stockReceiptId;
    private int productOptionId;
    private int quantity;
    private int price = 0;
    private ProductOptionDetailDto productOption;
}
