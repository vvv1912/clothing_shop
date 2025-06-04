package com.shop.clothing.stockReceipt.command.stockReceipt.createStockReceipt;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.stockReceipt.entity.StockReceipt;
import com.shop.clothing.stockReceipt.entity.StockReceiptItem;
import com.shop.clothing.stockReceipt.entity.Supplier;
import com.shop.clothing.stockReceipt.repository.StockReceiptItemRepository;
import com.shop.clothing.stockReceipt.repository.StockReceiptRepository;
import com.shop.clothing.stockReceipt.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class CreateStockReceiptCommandHandler implements IRequestHandler<CreateStockReceiptCommand, Integer> {
    private final StockReceiptRepository stockReceiptRepository;
    private final StockReceiptItemRepository stockReceiptItemRepository;
    private final ProductOptionRepository productOptionRepository;
    private final SupplierRepository supplierRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class, ResponseStatusException.class})

    public HandleResponse<Integer> handle(CreateStockReceiptCommand createStockReceiptCommand) {
        var existSupplier = supplierRepository.findById(createStockReceiptCommand.getSupplierId());
        if (existSupplier.isEmpty()) {
            return HandleResponse.error("Không tìm thấy nhà cung cấp");
        }
        var stockReceipt = StockReceipt.builder()
                .note(createStockReceiptCommand.getNote())
                .total(0)
                .supplier(existSupplier.get())
                .build();
        stockReceiptRepository.save(stockReceipt);
        int total = 0;
        for (CreateStockReceiptCommand.CreateItemCommand item : createStockReceiptCommand.getStockReceiptItems()) {
            var productOption = productOptionRepository.findById(item.productOptionId);
            if (productOption.isEmpty()) {
                throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Không tìm thấy sản phẩm với id " + item.productOptionId);
            }

            if (productOption.get().getProduct().getPrice() < item.price) {
                throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Giá nhập không được lớn hơn giá bán: sản phẩm " + productOption.get().getProduct().getName());
            }

            var stockReceiptItem = StockReceiptItem.builder()
                    .stockReceiptId(stockReceipt.getStockReceiptId())
                    .quantity(item.quantity)
                    .productOptionId(item.productOptionId)
                    .price(item.price)
                    .build();
            productOption.get().setStock(productOption.get().getStock() + item.quantity);
            productOptionRepository.save(productOption.get());
            stockReceiptItemRepository.save(stockReceiptItem);
            total += item.quantity * item.price;
        }
        stockReceipt.setTotal(total);
        stockReceiptRepository.save(stockReceipt);
        return HandleResponse.ok(stockReceipt.getStockReceiptId());
    }
}
