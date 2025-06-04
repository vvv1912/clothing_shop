package com.shop.clothing.product.command.createProductImage;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.entity.ProductImage;
import com.shop.clothing.product.repository.ProductImageRepository;
import com.shop.clothing.product.repository.ProductOptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CreateProductImageCommandHandler implements IRequestHandler<CreateProductImageCommand, Void> {

    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(CreateProductImageCommand createProductImageCommand) {
        var productOption = productOptionRepository.findFirstByProductIdAndColorId(createProductImageCommand.productId(), createProductImageCommand.colorId());
        if (productOption.isEmpty()) {
            return HandleResponse.error("Sản phẩm không tồn tại");
        }
        var productImage = ProductImage.builder().product(productOption.get().getProduct()).forColor(productOption.get().getColor()).url(createProductImageCommand.url()).build();
        productImageRepository.save(productImage);
        return HandleResponse.ok();
    }

}
