package com.shop.clothing.cart.query.getMyCart;

import com.shop.clothing.cart.CartRepository;
import com.shop.clothing.cart.dto.CartItemDto;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.config.CurrentUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class GetMyCartQueryHandler implements IRequestHandler<GetMyCartQuery, Collection<CartItemDto>> {
    private final CartRepository cartRepository;
    private final CurrentUserService currentUserService;
    private final ModelMapper modelMapper;
    @Override
    public HandleResponse<Collection<CartItemDto>> handle(GetMyCartQuery getMyCartQuery) {
        var currentUserId = currentUserService.getCurrentUserId();
        if (currentUserId.isEmpty()) {
            return HandleResponse.error("Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED);
        }
        var cartItems = cartRepository.findAllByUserId(currentUserId.get());
        var result= cartItems.stream().map(cartItem -> modelMapper.map(cartItem, CartItemDto.class)).toList();
        return HandleResponse.ok(result);
    }
}
