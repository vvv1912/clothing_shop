package com.shop.clothing.cart.command.addToCart;

import com.shop.clothing.cart.CartItem;
import com.shop.clothing.cart.CartRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.config.CurrentUserService;
import com.shop.clothing.config.ICurrentUserService;
import com.shop.clothing.product.entity.ProductOption;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AddToCartCommandHandler implements IRequestHandler<AddToCartCommand, Void> {
    private final CartRepository cartRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ICurrentUserService currentUserService;

    @Override
    @Transactional
    public HandleResponse<Void> handle(AddToCartCommand addToCartCommand) {
        var productOption = productOptionRepository.findById(addToCartCommand.getProductOptionId());
        if (productOption.isEmpty()) {
            return HandleResponse.error("Sản phẩm không tồn tại");
        }
        if (productOption.get().getStock() < addToCartCommand.getQuantity()) {
            return HandleResponse.error("Số lượng sản phẩm không đủ");
        }
        var currentUserId = currentUserService.getCurrentUserId();
        if (currentUserId.isEmpty()) {
            return HandleResponse.error("Bạn chưa đăng nhập");
        }
        var cartItem = CartItem.builder()
                .quantity(addToCartCommand.getQuantity())
                .userId(currentUserId.get())
                .productOptionId(addToCartCommand.getProductOptionId())
                .build();
        cartRepository.save(cartItem);
        return HandleResponse.ok();
    }
}
