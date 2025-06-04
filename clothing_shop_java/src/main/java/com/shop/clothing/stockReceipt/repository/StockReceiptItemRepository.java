package com.shop.clothing.stockReceipt.repository;

import com.shop.clothing.stockReceipt.entity.StockReceiptItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockReceiptItemRepository extends JpaRepository<StockReceiptItem, StockReceiptItem.StockReceiptItemKey> {
    boolean existsByProductOptionId(int productOptionId);
}
