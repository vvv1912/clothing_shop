package com.shop.clothing.cart.command.updateCartItemQuantity;

import com.shop.clothing.cart.CartRepository;
import com.shop.clothing.cart.command.addToCart.AddToCartCommand;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.config.ICurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UpdateCartItemQuantityCommandHandler implements IRequestHandler<UpdateCartItemQuantityCommand, Void> {
    private final CartRepository cartRepository;
    private final ICurrentUserService currentUserService;
    private final ISender sender;

    @Override
    @Transactional
    public HandleResponse<Void> handle(UpdateCartItemQuantityCommand updateCartItemQuantityCommand) {
        var currentUserId = currentUserService.getCurrentUserId();
        if (currentUserId.isEmpty()) {
            return HandleResponse.error("Bạn chưa đăng nhập");
        }
        var cartItem = cartRepository.findByUserIdAndProductOptionId(currentUserId.get(), updateCartItemQuantityCommand.productOptionId());
        if (cartItem.isEmpty()) {

            return sender.send( AddToCartCommand.builder().productOptionId(updateCartItemQuantityCommand.productOptionId()).quantity( updateCartItemQuantityCommand.newQuantity()).build());
        }
        var currentStock = cartItem.get().getProductOption().getStock();
        if (currentStock < updateCartItemQuantityCommand.newQuantity()) {
            return HandleResponse.error("Số lượng sản phẩm không đủ");
        }
        cartItem.get().setQuantity(updateCartItemQuantityCommand.newQuantity());
        cartRepository.save(cartItem.get());
        return HandleResponse.ok();
    }
}
