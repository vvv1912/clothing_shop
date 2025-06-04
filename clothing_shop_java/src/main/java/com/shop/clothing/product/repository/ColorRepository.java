package com.shop.clothing.product.repository;

import com.shop.clothing.product.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository  extends JpaRepository<Color,Integer> {
    Optional<Color> findByNameIgnoreCase(String name);
}
