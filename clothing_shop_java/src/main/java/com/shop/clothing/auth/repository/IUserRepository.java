package com.shop.clothing.auth.repository;

import com.shop.clothing.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE concat(u.firstName, ' ', u.lastName) LIKE %?1%"
            + " OR u.email LIKE %?1%"
            + " OR u.phoneNumber LIKE %?1%"
            + "  and u.isCustomer = true")
    Page<User> searchCustomer(String keyword, Pageable pageable);

    @Query("SELECT u FROM User u WHERE concat(u.firstName, ' ', u.lastName) LIKE %?1%"
            + " OR u.email LIKE %?1%"
            + " OR u.phoneNumber LIKE %?1%"
            + "  and u.isCustomer = false")
    Page<User> searchEmployee(String keyword, Pageable pageable);

    @Query("SELECT u FROM User u WHERE concat(u.firstName, ' ', u.lastName) LIKE %?1%"
            + " OR u.email LIKE %?1%"
            + " OR u.phoneNumber LIKE %?1%")
    Page<User> search(String keyword, Pageable pageable);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u JOIN u.roles r WHERE u.userId = ?1 AND r.normalizedName = ?2")
    boolean isInRole(String userId, String roleName);
}
