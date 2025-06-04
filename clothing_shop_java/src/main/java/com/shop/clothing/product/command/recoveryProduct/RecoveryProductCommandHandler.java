package com.shop.clothing.product.command.recoveryProduct;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RecoveryProductCommandHandler implements IRequestHandler<RecoveryProductCommand, Void> {
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(RecoveryProductCommand recoveryProductCommand) {
        productRepository.recoveryByProductId(recoveryProductCommand.product());
        return HandleResponse.ok();
    }
}
