package com.shop.clothing.rating;

import com.shop.clothing.product.entity.ProductOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface RatingRepository extends JpaRepository<Rating,Integer> {

    @Query("select r from Rating r where r.productOption.product.productId = ?1")
    Page<Rating> findAllByProductId(int productId, Pageable pageable);
List<Rating> findAllByProductOptionProductProductId(int productId);
    @Query ("select r from Rating r where r.user.userId = ?1 and r.productOption.productOptionId = ?2 and r.order.orderId = ?3")
    Optional<Rating> findFirstByUserIdAndProductOptionIdAndOrderId(String userId, int productOptionId, String orderId);
}
