package com.shop.clothing.stockReceipt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
@SQLDelete(sql = "UPDATE product SET deleted_date = NOW() WHERE product_id = ?")
@Where(clause = "deleted_date is null")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supplier")

    private java.util.List<StockReceipt> stockReceipts;
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate = null;
}
