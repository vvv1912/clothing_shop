package com.shop.clothing.product.command.createAndGetProductOption;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.product.dto.ProductOptionDetailDto;
import com.shop.clothing.product.entity.Color;
import com.shop.clothing.product.entity.ProductOption;
import com.shop.clothing.product.repository.ColorRepository;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateAndGetProductOptionCommandHandler implements IRequestHandler<CreateAndGetProductOptionCommand, ProductOptionDetailDto> {
    private final ProductOptionRepository productOptionRepository;
    private final ColorRepository colorRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    @Override
    public HandleResponse<ProductOptionDetailDto> handle(CreateAndGetProductOptionCommand command) throws Exception {
        command.setSize(command.getSize().toUpperCase());
        var product = productRepository.findById(command.getProductId());
        if (product.isEmpty()) {
            return HandleResponse.error("Sản phẩm không tồn tại");
        }
        var colorOptional = colorRepository.findByNameIgnoreCase(command.getColorName());
        Color color = null;
        if (colorOptional.isEmpty()) {
            var newColor = new Color();
            newColor.setName(command.getColorName());
            newColor.setImage(null);
            colorRepository.save(newColor);
            color = newColor;
        } else {
            color = colorOptional.get();
        }


        var productOption = productOptionRepository.findFirstByProductIdAndColorIdAndSize(command.getProductId(), color.getColorId(), command.getSize());
        if (productOption.isPresent()) {
            if (productOption.get().getDeletedDate() != null) {
                productOption.get().setDeletedDate(null);
                productOptionRepository.save(productOption.get());
                return HandleResponse.ok();
            }
            var dto = modelMapper.map(productOption,ProductOptionDetailDto.class);
            return HandleResponse.ok(dto);
        }
        var newProductOption = ProductOption.builder().product(product.get()).color(color).size(command.getSize()).stock(command.getStock()).build();
        productOptionRepository.save(newProductOption);
        var dto = modelMapper.map(newProductOption,ProductOptionDetailDto.class);
        return HandleResponse.ok(dto);
    }
}
