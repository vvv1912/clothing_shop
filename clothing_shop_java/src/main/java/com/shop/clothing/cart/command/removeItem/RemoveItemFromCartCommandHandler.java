package com.shop.clothing.cart.command.removeItem;

import com.shop.clothing.cart.CartRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.config.ICurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RemoveItemFromCartCommandHandler implements IRequestHandler<RemoveItemFromCartCommand, Void> {
    private final CartRepository cartRepository;
    private final ICurrentUserService currentUserService;

    @Override
    @Transactional
    public HandleResponse<Void> handle(RemoveItemFromCartCommand removeItemFromCartCommand) {
        var currentUserId = currentUserService.getCurrentUserId();
        if (currentUserId.isEmpty()) {
            return HandleResponse.error("Bạn chưa đăng nhập");
        }
        var cartItem = cartRepository.findByUserIdAndProductOptionId(currentUserId.get(), removeItemFromCartCommand.productOptionId());
        if (cartItem.isEmpty()) {
            return HandleResponse.error("Sản phẩm không tồn tại trong giỏ hàng");
        }
        cartRepository.delete(cartItem.get());
        return HandleResponse.ok();
    }
}
