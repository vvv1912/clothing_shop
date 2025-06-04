package com.shop.clothing.stockReceipt.dto;


import com.shop.clothing.common.dto.AuditableDto;
import com.shop.clothing.stockReceipt.entity.StockReceiptItem;
import com.shop.clothing.stockReceipt.entity.Supplier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockReceiptBriefDto extends AuditableDto {
    private int stockReceiptId;
    private int total = 0;
    private String note;
    private int supplierId;
    private SupplierDto supplier;
}
