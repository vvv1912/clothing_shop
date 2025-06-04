package com.shop.clothing.report.query.getCategoryReport;

import com.shop.clothing.category.CategoryBriefDto;
import com.shop.clothing.category.CategoryRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.report.dto.CategoryReportDto;
import com.shop.clothing.report.query.getTopSoldProductReport.GetTopSoldProductReportQuery;
import com.shop.clothing.report.query.getTopSoldProductReport.GetTopSoldProductReportQueryHandler;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor

public class GetReportByCategoryQueryHandler implements IRequestHandler<GetReportByCategoryQuery, List<CategoryReportDto>> {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ISender sender;
    private final EntityManager entityManager;

    @Getter
    @Setter
    @AllArgsConstructor
    private static class QueryResult {
        private Integer id;
        private String name;
        private Integer parentId;
        private Long totalProducts;
        private Long totalSold;
        private Long totalRevenue;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "categoryReport", key = "#getReportByCategoryQuery.toString()")
    public HandleResponse<List<CategoryReportDto>> handle(GetReportByCategoryQuery getReportByCategoryQuery) throws Exception {
        var rawQuery = """
                SELECT
                    c.category_id as id,
                    c.name as name,
                    c.parent_category_id as parent_id,
                    COUNT(p.product_id) AS total_products,
                    CAST(COALESCE(SUM(oi.quantity), 0) AS INTEGER) AS total_sold,
                    CAST(COALESCE(SUM(oi.quantity * oi.price), 0) AS INTEGER) AS total_revenue
                FROM category c
                         LEFT JOIN product p ON p.category_category_id = c.category_id
                         LEFT JOIN product_option po ON po.product_product_id = p.product_id
                         LEFT JOIN order_item oi ON oi.product_option_id = po.product_option_id
                         LEFT JOIN _order o ON o.order_id = oi.order_id
                WHERE (o.completed_date IS NOT NULL  AND (?1 is null or o.completed_date >= ?1)
                     AND (?2 is null or o.completed_date <= ?2))
                   OR o.order_id IS NULL
                GROUP BY c.category_id""";
        var query = entityManager.createNativeQuery(rawQuery, QueryResult.class);
        query.setParameter(1, getReportByCategoryQuery.getStartDate());
        query.setParameter(2, getReportByCategoryQuery.getEndDate());
        List<QueryResult> resultList = query.getResultList().stream().toList();
        Map<Integer, CategoryReportDto> categoryReportDtoMap = categoryRepository.findAll().stream().map((c) -> {
            var categoryReportDto = new CategoryReportDto();
            categoryReportDto.setCategory(modelMapper.map(c, CategoryBriefDto.class));
            return categoryReportDto;
        }).collect(java.util.stream.Collectors.toMap((c) -> c.getCategory().getCategoryId(), (c) -> c));
        resultList.forEach((r) -> {
            var categoryReportDto = categoryReportDtoMap.get(r.getId());
            categoryReportDto.setTotalProducts(Math.toIntExact(r.getTotalProducts()));
            categoryReportDto.setTotalSoldProducts(Math.toIntExact(r.getTotalSold()));
            categoryReportDto.setTotalRevenue(r.getTotalRevenue());
            if (r.getParentId() != null) {
                var parentCategoryReportDto = categoryReportDtoMap.get(r.getParentId());
                parentCategoryReportDto.setTotalProducts((int) (parentCategoryReportDto.getTotalProducts() + r.getTotalProducts()));
                parentCategoryReportDto.setTotalSoldProducts((int) (parentCategoryReportDto.getTotalSoldProducts() + r.getTotalSold()));
                parentCategoryReportDto.setTotalRevenue(parentCategoryReportDto.getTotalRevenue() + r.getTotalRevenue());
            }
        });
        return HandleResponse.ok(categoryReportDtoMap.values().stream().toList());

    }
}
