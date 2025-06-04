package com.shop.clothing.rating;


import com.shop.clothing.common.AuditableEntity;
import com.shop.clothing.order.entity.Order;
import com.shop.clothing.order.entity.OrderItem;
import com.shop.clothing.product.entity.Product;
import com.shop.clothing.product.entity.ProductOption;
import com.shop.clothing.user.entity.User;
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

public class Rating extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private int value;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOption productOption;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
}
