package com.shop.clothing.cart;

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
@IdClass(CartItem.CartItemKey.class)
public class CartItem {
    @AllArgsConstructor
    @NoArgsConstructor

    static
    class CartItemKey implements java.io.Serializable{
        private String userId;
        private int productOptionId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CartItemKey that = (CartItemKey) o;
            return productOptionId == that.productOptionId && Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, productOptionId);
        }
    }
    @Id
    @Column(updatable = false, nullable = false,name = "user_id")
    private String userId;
    @Id
    @Column(updatable = false, nullable = false,name = "product_option_id")
    private int productOptionId;
    private int quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_option_id", insertable = false, updatable = false)
    private ProductOption productOption;
}

