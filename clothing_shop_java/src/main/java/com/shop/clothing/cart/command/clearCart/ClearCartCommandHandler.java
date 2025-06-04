package com.shop.clothing.cart.command.clearCart;

import com.shop.clothing.cart.CartRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.config.ICurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ClearCartCommandHandler implements IRequestHandler<ClearCartCommand, Integer> {
    private final CartRepository cartRepository;
    private final ICurrentUserService currentUserService;


    @Override
    @Transactional
    public HandleResponse<Integer> handle(ClearCartCommand clearCartCommand) {
        var currentUserId = currentUserService.getCurrentUserId();
        if (currentUserId.isEmpty()) {
            return HandleResponse.error("Bạn chưa đăng nhập");
        }
        return HandleResponse.ok(cartRepository.deleteAllByUserId(currentUserId.get()));
    }
}
