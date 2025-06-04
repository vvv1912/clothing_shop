package com.shop.clothing.product.query.getAllProducts;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.product.dto.ProductBriefDto;
import com.shop.clothing.product.entity.Product;
import com.shop.clothing.product.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Service
public class GetAllProductsQueryHandler implements IRequestHandler<GetAllProductsQuery, Paginated<ProductBriefDto>> {
    private final ProductRepository productRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public HandleResponse<Paginated<ProductBriefDto>> handle(GetAllProductsQuery query) {

        String sortField = query.getSortField();
        if (sortField.isBlank()) {
            sortField = "created_date";
        }
        var paged = productRepository.simpleSearch(query.getKeyword(), query.getCategoryId(), query.getForGender(), query.getMinPrice(), query.getMaxPrice(), query.getPageable(sortField));

        var productBriefDtos = paged.map(product -> {
            return modelMapper.map(product, ProductBriefDto.class);
        });

        return HandleResponse.ok(Paginated.of(productBriefDtos));
    }
}
