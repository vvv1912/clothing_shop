package com.shop.clothing.category;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findAllByParentCategoryId(Integer parentCategoryId, org.springframework.data.domain.Pageable pageable);

    Page<Category> findAllByParentCategoryIdIsNull(org.springframework.data.domain.Pageable pageable);

    List<Category> findAllByParentCategoryIdIsNull();

    Page<Category> findAllByParentCategoryIdIsNotNull(org.springframework.data.domain.Pageable pageable);

    Page<Category> findAllByNameContaining(String keyword, org.springframework.data.domain.Pageable pageable);

    Optional<Category> findByName(String name);

    Page<Category> findAllByNameContainingIgnoreCase(String name, org.springframework.data.domain.Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM category WHERE category_id = ?1", nativeQuery = true)
    void hardDeleteById(Integer id);

    @Query(value = "select c.categoryId from Category  c where c.parent.categoryId in ?1")
    List<Integer> findAllByParentCategoryIdIn(int[] ids);
}
