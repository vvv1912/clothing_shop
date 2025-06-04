package com.shop.clothing.product.query.getProductOptionById;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.dto.ProductOptionDetailDto;
import com.shop.clothing.product.repository.ProductOptionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class GetProductOptionByIdQueryHandler implements IRequestHandler<GetProductOptionByIdQuery, ProductOptionDetailDto> {
   private final ProductOptionRepository productOptionRepository;
   private final ModelMapper modelMapper;
    @Override
    @Transactional(readOnly = true)
    public HandleResponse<ProductOptionDetailDto> handle(GetProductOptionByIdQuery getProductOptionByIdQuery) throws Exception {
        var productOption = productOptionRepository.findById(getProductOptionByIdQuery.id()).orElse(null);
        if (productOption == null) {
            return HandleResponse.error("Không tìm thấy sản phẩm");
        }
        return HandleResponse.ok(modelMapper.map(productOption, ProductOptionDetailDto.class));
    }
}
