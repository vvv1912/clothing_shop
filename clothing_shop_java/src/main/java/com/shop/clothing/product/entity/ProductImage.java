package com.shop.clothing.product.entity;


import com.shop.clothing.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "product_image")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductImage extends AuditableEntity{
    @Id
    @Column(updatable = false, nullable = false,name = "url",length = 500)
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    private Color forColor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
