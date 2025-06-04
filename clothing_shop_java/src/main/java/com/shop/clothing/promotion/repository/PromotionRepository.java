package com.shop.clothing.promotion.repository;

import com.shop.clothing.promotion.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    Optional<Promotion> findByCodeIgnoreCase(String code);


    @Query("SELECT p FROM Promotion p WHERE " +
            "(:keyword IS NULL OR p.code LIKE %:keyword% OR p.name LIKE %:keyword%) AND " +
            "(:fromDate IS NULL OR p.startDate >= :fromDate) AND " +
            "(:toDate IS NULL OR p.endDate <= :toDate)")

    Page<Promotion> search(
            String keyword,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Pageable pageable
    );
}
