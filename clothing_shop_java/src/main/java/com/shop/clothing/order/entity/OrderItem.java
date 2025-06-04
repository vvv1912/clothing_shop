package com.shop.clothing.order.entity;

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
@IdClass(OrderItem.OrderItemKey.class)
public class OrderItem {
    @AllArgsConstructor
    @NoArgsConstructor
  public   static
    class OrderItemKey implements java.io.Serializable{
        private String orderId;
        private int productOptionId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderItemKey that = (OrderItemKey) o;
            return productOptionId == that.productOptionId && Objects.equals(orderId, that.orderId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, productOptionId);
        }
    }

    @Id
    @Column(updatable = false, nullable = false,name = "order_id")
    private String orderId;
    @Id
    @Column(updatable = false, nullable = false,name = "product_option_id")
    private int productOptionId;
    private int quantity;
    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_option_id", insertable = false, updatable = false)
    private ProductOption productOption;

}
