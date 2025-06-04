package com.shop.clothing.auth.repository;

import com.shop.clothing.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    @Query("SELECT p FROM Permission p WHERE p.normalizedName = ?1")
    Optional<Permission> findByName(String name);
}
