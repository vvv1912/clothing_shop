package com.shop.clothing.product.repository;

import com.shop.clothing.product.entity.Product;
import com.shop.clothing.product.entity.ProductOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {


    @Query(value = "select * from product_option po where po.product_product_id = ?1 and po.color_color_id = ?2 and po.size = ?3 order by po.color_color_id desc limit 1", nativeQuery = true)
    Optional<ProductOption> findFirstByProductIdAndColorIdAndSize(Integer productId, Integer colorId, String size);


    @Query(value = "select * from product_option po where po.product_product_id = ?1 and po.color_color_id = ?2 order by po.created_date desc limit 1", nativeQuery = true)
    Optional<ProductOption> findFirstByProductIdAndColorId(Integer productId, Integer colorId);

    @Modifying()

    @Query(value = "DELETE FROM  product_option  WHERE product_product_id = ?1", nativeQuery = true)
    void hardDeleteById(int id);

    List<ProductOption> findAllByProductOptionIdIn(List<Integer> ids);

    @Query(value = "select p from ProductOption p where p.product.name = ?1 and ?1 like concat('%', p.size, '%') and ?1 like concat('%', p.color.name, '%')")
    Page<ProductOption> search(String keyword, Pageable pageable);

    @Query(value = "select distinct po.size from product_option po", nativeQuery = true)
    List<String> getAllSizes();

    @Modifying
    @Query(value = "update ProductOption po set po.deletedDate = now() where po.product.productId = ?1")
    void deleteAllByProductId(int productId);

    @Modifying
    @Query(value = "update product_option po set po.deleted_date = null where po.product_option_id = ?1", nativeQuery = true)
    void recoveryByProductOptionId(int productOptionId);

    @Modifying
    @Query(value = "update product_option po set po.deleted_date = null where po.product_product_id = ?1", nativeQuery = true)
    void recoveryByProductId(int productOptionId);

    List<ProductOption> findByProductProductId(int productId);

    @Query(value = "select * from product_option po where po.product_product_id = ?1 and po.deleted_date is not null", nativeQuery = true)
    List<ProductOption> getDeletedProductOptionsByProductId(int productId);
}
