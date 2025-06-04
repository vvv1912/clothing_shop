package com.shop.clothing.product.entity;

import com.shop.clothing.category.Category;
import com.shop.clothing.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
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
@SQLDelete(sql = "UPDATE product SET deleted_date = NOW() WHERE product_id=?")
@Where(clause = "deleted_date is null")
public class Product extends AuditableEntity {
    public enum ProductGender {
        FOR_MALE,
        FOR_FEMALE,
        FOR_BOTH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "product_id")
    private int productId;


    @Column(nullable = false)


    private String name;

    @Column(nullable = false)
    private ProductGender forGender = ProductGender.FOR_BOTH;

    @Column(nullable = false, unique = true)
    private String slug;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;


    @Column(nullable = false)
    private int price;


    @Column(nullable = false)
    private int discount;


    @Column(nullable = false)
    private String displayImage;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate = null;

//    @ManyToOne()
//    private Brand brand;


    @ManyToOne
    private Category category;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private java.util.List<ProductOption> productOptions;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private java.util.List<ProductImage> images;

    private int totalSold = 0;
    @Column(nullable = false,columnDefinition = "float default 0.0")
    private float averageRating = 0.0f;

    public int getFinalPrice() {
        return (int) (price - price * discount / 100.0);
    }

}
