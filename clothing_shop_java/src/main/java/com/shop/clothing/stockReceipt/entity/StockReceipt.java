package com.shop.clothing.stockReceipt.entity;

import com.shop.clothing.common.AuditableEntity;
import com.shop.clothing.order.entity.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table

public class StockReceipt extends AuditableEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false,name = "stock_receipt_id")
    private int stockReceiptId;

    @Column(nullable = false)
    private int total = 0;

    @Column(length = 255)
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    private Supplier supplier;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_receipt_id")
    private java.util.List<StockReceiptItem> stockReceiptItems;



}
