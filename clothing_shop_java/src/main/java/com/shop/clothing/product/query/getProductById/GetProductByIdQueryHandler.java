package com.shop.clothing.product.query.getProductById;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.dto.ProductDetailDto;
import com.shop.clothing.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class GetProductByIdQueryHandler implements IRequestHandler<GetProductByIdQuery, ProductDetailDto> {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public HandleResponse<ProductDetailDto> handle(GetProductByIdQuery getProductBySlugQuery) {
        var product = productRepository.findByIdIncludeDeleted(getProductBySlugQuery.id());
        if (product.isEmpty()) {
            return HandleResponse.error("Product not found", HttpStatus.NOT_FOUND);
        }
        return HandleResponse.ok(modelMapper.map(product.get(), ProductDetailDto.class));
    }
}
