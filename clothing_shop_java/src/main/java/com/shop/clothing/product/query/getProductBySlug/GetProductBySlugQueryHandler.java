package com.shop.clothing.product.query.getProductBySlug;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.dto.ProductDetailDto;
import com.shop.clothing.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetProductBySlugQueryHandler implements IRequestHandler<GetProductBySlugQuery, ProductDetailDto> {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public HandleResponse<ProductDetailDto> handle(GetProductBySlugQuery getProductBySlugQuery) {
        var product = productRepository.findBySlug(getProductBySlugQuery.slug());
        if (product.isEmpty()) {
            return HandleResponse.ok(null);
        }
        return HandleResponse.ok(modelMapper.map(product.get(), ProductDetailDto.class));
    }
}
