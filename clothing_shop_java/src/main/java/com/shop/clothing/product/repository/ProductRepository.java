package com.shop.clothing.product.repository;

import com.shop.clothing.order.entity.enums.OrderStatus;
import com.shop.clothing.product.entity.Product;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // has id in array
    List<Product> findByProductIdIn(Collection<Integer> productId);

    @Query(value = "SELECT * FROM product p WHERE  p.product_id = ?1", nativeQuery = true)
    Optional<Product> findByIdIncludeDeleted(int id);

    Optional<Product> findBySlug(String slug);

    @Modifying

    @Query(value = "delete from product where product_id = ?1", nativeQuery = true)
    void hardDeleteById(int id);

    @Query(value = "SELECT DISTINCT  p.product  FROM ProductOption p WHERE " +
            " (:keyword IS NULL OR p.product.name LIKE %:keyword%)" +
            "AND  (:categoryIds IS NULL   or p.product.category.categoryId IN :categoryIds)" +
            " AND (:forGenders IS NULL   or p.product.forGender IN :forGenders)" +
            " AND (p.product.price >= :minPrice)" +
            " AND ( p.product.price <= :maxPrice)" +
            " AND (p.deletedDate IS NULL)" +
            " AND (:colorIds IS NULL   or p.color.colorId IN :colorIds)" +
            " AND (:sizes IS NULL   or p.size IN :sizes)",
            countQuery = "SELECT count(DISTINCT p.product.productId) FROM ProductOption p WHERE " +
                    " (:keyword IS NULL OR p.product.name LIKE %:keyword%)" +
                    "AND  (:categoryIds IS NULL   or p.product.category.categoryId IN :categoryIds)" +
                    " AND (:forGenders IS NULL   or p.product.forGender IN :forGenders)" +
                    " AND (p.product.price >= :minPrice)" +
                    " AND ( p.product.price <= :maxPrice)" +
                    " AND (p.deletedDate IS NULL)" +
                    " AND (:colorIds IS NULL   or p.color.colorId IN :colorIds)" +
                    " AND (:sizes IS NULL   or p.size IN :sizes)"

    )
    Page<Product> searchAllProducts(String keyword, int[] categoryIds, Product.ProductGender[] forGenders,
                                    int minPrice, int maxPrice,
                                    int[] colorIds, String[] sizes,
                                    Pageable pageable);

    @Modifying
    @Query(value = "update product p set p.deleted_date = null where p.product_id = ?1", nativeQuery = true)
    void recoveryByProductId(int productId);

    @Query(nativeQuery = true, value = "select * from product p " +
            " WHERE (:categoryId is null OR p.category_category_id = :categoryId)" +
            "         AND (:minPrice IS NULL OR p.price >= :minPrice)" +
            "         AND (:maxPrice IS NULL OR p.price <= :maxPrice)" +
            "AND (:forGender is null or p.for_gender = :forGender)" +
            "AND (:keyword is null or p.name LIKE  CONCAT('%', :keyword ,'%'))",
            countQuery =
                    "select count(*) from product p " +
                            " WHERE (:categoryId is null OR p.category_category_id = :categoryId)" +
                            "         AND (:minPrice IS NULL OR p.price >= :minPrice)" +
                            "         AND (:maxPrice IS NULL OR p.price <= :maxPrice)" +
                            "AND (:forGender is null or p.for_gender = :forGender)" +
                            "AND (:keyword is null or p.name LIKE  CONCAT('%', :keyword ,'%'))"

    )
    Page<Product> simpleSearch(String keyword, Integer categoryId, Product.ProductGender forGender, Integer minPrice, Integer maxPrice, Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.totalSold =  (" +
            "SELECT SUM(oi.quantity) FROM OrderItem oi " +
            "JOIN Order o on oi.orderId = o.orderId " +
            "WHERE oi.productOption.product.productId = p.productId AND o.status IN :statuses " +
            ")")
    void updateTotalSold(OrderStatus[] statuses);

    @Modifying
    @Query("UPDATE Product p SET p.totalSold = 0 WHERE p.totalSold IS NULL")
    void updateTotalToZeroWhereSoldIsNull();

    @Query(value = "SELECT  p.product_id AS productId" +
            ", CAST(COALESCE(SUM(oi.quantity), 0) AS int) AS total_sold, CAST(COALESCE(SUM(oi.quantity * oi.price), 0) AS int) AS total_revenue " +
            "FROM product p " +
            "LEFT  JOIN product_option po ON po.product_product_id = p.product_id " +
            "LEFT JOIN order_item oi ON oi.product_option_id = po.product_option_id " +
            "LEFT JOIN `_order` o ON o.order_id = oi.order_id " +
            "WHERE (o.completed_date IS NOT NULL AND (?1 is null or o.completed_date >= ?1) " +
            "AND (?2 is null or o.completed_date <= ?2) ) " +
            "OR o.order_id IS NULL " +
            "GROUP BY p.product_id ", nativeQuery = true)
    List<Tuple> getSoldReport(Date startDate, Date endDate);
}
