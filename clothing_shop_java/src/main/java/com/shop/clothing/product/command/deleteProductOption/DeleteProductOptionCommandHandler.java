package com.shop.clothing.product.command.deleteProductOption;

import com.shop.clothing.cart.CartRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.order.repository.OrderItemRepository;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.product.entity.ProductOption;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.stockReceipt.entity.StockReceiptItem;
import com.shop.clothing.stockReceipt.repository.StockReceiptItemRepository;
import com.shop.clothing.stockReceipt.repository.StockReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class DeleteProductOptionCommandHandler implements IRequestHandler<DeleteProductOptionCommand, Void> {
    private final ProductOptionRepository productOptionRepository;
    private final OrderItemRepository orderItemRepository;
    private final StockReceiptItemRepository stockReceiptItemRepository;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(DeleteProductOptionCommand deleteProductOptionCommand) {
        var productOption = productOptionRepository.findById(deleteProductOptionCommand.id());
        if (productOption.isEmpty()) {
            return HandleResponse.error("Mẫu sản phẩm không tồn tại");
        }
        var canNotDelete = orderItemRepository.existsByProductOptionId(deleteProductOptionCommand.id()) ||
                stockReceiptItemRepository.existsByProductOptionId(deleteProductOptionCommand.id());
        if (canNotDelete) {
            System.out.println("soft delete");
            productOptionRepository.delete(productOption.get());

        } else {
            System.out.println("hard delete");

            productOptionRepository.hardDeleteById(deleteProductOptionCommand.id());
        }
        cartRepository.deleteAllByProductOptionIdIn(List.of(deleteProductOptionCommand.id()));
        return HandleResponse.ok();
    }
}
