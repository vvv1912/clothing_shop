package com.shop.clothing.product.command.createProductOption;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.entity.Color;
import com.shop.clothing.product.entity.ProductOption;
import com.shop.clothing.product.repository.ColorRepository;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CreateProductOptionCommandHandler implements IRequestHandler<CreateProductOptionCommand, Integer> {
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;

    @Override
    @Transactional
    public HandleResponse<Integer> handle(CreateProductOptionCommand createProductOptionCommand) {
        createProductOptionCommand.setSize(createProductOptionCommand.getSize().toUpperCase());
        var product = productRepository.findById(createProductOptionCommand.getProductId());
        if (product.isEmpty()) {
            return HandleResponse.error("Sản phẩm không tồn tại");
        }
        var colorOptional = colorRepository.findByNameIgnoreCase(createProductOptionCommand.getColorName());
        Color color = null;
        if (colorOptional.isEmpty()) {
            var newColor = new Color();
            newColor.setName(createProductOptionCommand.getColorName());
            newColor.setImage(null);
            colorRepository.save(newColor);
            color = newColor;
        } else {
            color = colorOptional.get();
        }


        var productOption = productOptionRepository.findFirstByProductIdAndColorIdAndSize(createProductOptionCommand.getProductId(), color.getColorId(), createProductOptionCommand.getSize());
        if (productOption.isPresent()) {
            if (productOption.get().getDeletedDate() != null) {
                productOption.get().setDeletedDate(null);
                productOptionRepository.save(productOption.get());
                return HandleResponse.ok();
            }
            return HandleResponse.error("Mẫu sản phẩm đã tồn tại");
        }
        var newProductOption = ProductOption.builder().product(product.get()).color(color).size(createProductOptionCommand.getSize()).stock(createProductOptionCommand.getStock()).build();
        productOptionRepository.save(newProductOption);
        return HandleResponse.ok(newProductOption.getProductOptionId());
    }

}
