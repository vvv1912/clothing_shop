package com.shop.clothing.product.command.updateProduct;

import com.shop.clothing.category.CategoryRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.util.SlugUtil;
import com.shop.clothing.product.entity.Product;
import com.shop.clothing.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UpdateProductCommandHandler implements IRequestHandler<UpdateProductCommand, Void> {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SlugUtil slugUtil;

    @Override
    @Transactional
    public HandleResponse<Void> handle(UpdateProductCommand updateProductCommand) {
        var existProduct = productRepository.findById(updateProductCommand.getProductId());
        if (existProduct.isEmpty()) {
            return HandleResponse.error("Sản phẩm không tồn tại");
        }
        if (updateProductCommand.getCategoryId() != existProduct.get().getCategory().getCategoryId()) {
            var existCategory = categoryRepository.findById(updateProductCommand.getCategoryId());
            if (existCategory.isEmpty()) {
                return HandleResponse.error("Danh mục sản phẩm không tồn tại");
            }
            existProduct.get().setCategory(existCategory.get());
        }
        var product = existProduct.get();
        product.setName(updateProductCommand.getName());
        product.setForGender(updateProductCommand.getForGender());
        product.setDescription(updateProductCommand.getDescription());
        product.setPrice(updateProductCommand.getPrice());
        product.setDiscount(updateProductCommand.getDiscount());
        product.setDisplayImage(updateProductCommand.getDisplayImage());

        productRepository.save(product);
        return HandleResponse.ok();
    }
}
