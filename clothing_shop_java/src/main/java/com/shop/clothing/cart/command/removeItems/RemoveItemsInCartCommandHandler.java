package com.shop.clothing.cart.command.removeItems;

import com.shop.clothing.cart.CartRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RemoveItemsInCartCommandHandler implements IRequestHandler<RemoveItemsInCartCommand, Void> {
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(RemoveItemsInCartCommand removeItemsInCartCommand) throws Exception {
        cartRepository.deleteAllByProductOptionIdIn(removeItemsInCartCommand.getProductOptionIds());
        return HandleResponse.ok();
    }
}
