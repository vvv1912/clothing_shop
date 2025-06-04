package com.shop.clothing.category;

import com.shop.clothing.common.AuditableEntity;
import com.shop.clothing.product.entity.Product;
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
@SQLDelete(sql = "UPDATE category SET deleted_date = NOW() WHERE category_id=?")
@Where(clause = "deleted_date is null")
public class Category extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "category_id")
    private int categoryId;


    @Column(unique = true, nullable = false, length = 100)
    private String name;




    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate = null;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private java.util.List<Product> products;


    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private java.util.List<Category> children;
}
