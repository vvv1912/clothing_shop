package com.shop.clothing.product.query.advanceSearchProduct;

import com.shop.clothing.category.CategoryRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.product.dto.ProductBriefDto;
import com.shop.clothing.product.entity.Product;
import com.shop.clothing.product.query.getAllProducts.GetAllProductsQuery;
import com.shop.clothing.product.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@AllArgsConstructor
@Service
public class AdvanceSearchAllProductsQueryHandler implements IRequestHandler<AdvanceSearchAllProductsQuery, Paginated<ProductBriefDto>> {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public HandleResponse<Paginated<ProductBriefDto>> handle(AdvanceSearchAllProductsQuery query) throws Exception {
        if (query.getSizes() != null)
            query.setSizes(Arrays.stream(query.getSizes()).map(String::toUpperCase).toArray(String[]::new));
        var categoryIds = categoryRepository.findAllByParentCategoryIdIn(query.getCategoryIds());
        if (query.getCategoryIds() != null) {
            categoryIds.addAll(Arrays.stream(query.getCategoryIds()).boxed().toList());
        }
        query.setCategoryIds(categoryIds.stream().mapToInt(Integer::intValue).toArray());
        if (query.getCategoryIds().length == 0) {
            query.setCategoryIds(null);
        }
        var productPage = productRepository.searchAllProducts(query.getKeyword(), query.getCategoryIds(), query.getForGenders(), query.getMinPrice(), query.getMaxPrice(), query.getColorIds(), query.getSizes(), query.getPageable("product.createdDate"));
        Paginated<ProductBriefDto> paginated = Paginated.of(productPage.map(product -> modelMapper.map(product, ProductBriefDto.class)));
        return HandleResponse.ok(paginated);
    }
}
