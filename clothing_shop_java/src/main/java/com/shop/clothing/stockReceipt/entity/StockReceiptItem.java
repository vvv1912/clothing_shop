package com.shop.clothing.stockReceipt.entity;

import com.shop.clothing.order.entity.OrderItem;
import com.shop.clothing.product.entity.ProductOption;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table()
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@IdClass(StockReceiptItem.StockReceiptItemKey.class)
public class StockReceiptItem {

    public static class StockReceiptItemKey implements java.io.Serializable {
        public int stockReceiptId;
        public int productOptionId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StockReceiptItemKey that = (StockReceiptItemKey) o;
            return stockReceiptId == that.stockReceiptId && productOptionId == that.productOptionId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(stockReceiptId, productOptionId);
        }
    }

    @Id
    @Column(updatable = false, nullable = false, name = "stock_receipt_id")
    private int stockReceiptId;
    @Id
    @Column(updatable = false, nullable = false, name = "product_option_id")
    private int productOptionId;

    private int quantity;
    private int price = 0; //gia nhap
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_option_id", insertable = false, updatable = false)
    private ProductOption productOption;
}
