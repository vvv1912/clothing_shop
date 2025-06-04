package com.shop.clothing.product.entity;

import com.shop.clothing.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public
class Color extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "color_id")
    private int colorId;
    private String name;
    private String image;


    @OneToMany(mappedBy = "color", fetch = FetchType.LAZY)
    private List<ProductOption> productOptions;
    @OneToMany(mappedBy = "forColor", fetch = FetchType.LAZY)
    private List<ProductImage> productImages;
}
