package com.shop.clothing.order.repository;

import com.shop.clothing.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItem.OrderItemKey> {
    boolean existsByProductOptionId(int productOptionId);
}
