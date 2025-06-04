package com.shop.clothing.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, CartItem.CartItemKey> {
    int deleteAllByUserId(String userId);

    Collection<CartItem> findAllByUserId(String userId);

    Optional<CartItem> findByUserIdAndProductOptionId(String userId, Integer productOptionId);

    void deleteAllByProductOptionIdIn(Collection<Integer> productOptionIds);

}
