package com.shop.clothing.stockReceipt.command.stockReceipt.deleteStockReceipt;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.stockReceipt.command.stockReceipt.createStockReceipt.CreateStockReceiptCommand;
import com.shop.clothing.stockReceipt.entity.StockReceipt;
import com.shop.clothing.stockReceipt.entity.StockReceiptItem;
import com.shop.clothing.stockReceipt.repository.StockReceiptItemRepository;
import com.shop.clothing.stockReceipt.repository.StockReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class DeleteStockReceiptCommandHandler implements IRequestHandler<DeleteStockReceiptCommand, Void> {
    private final StockReceiptRepository stockReceiptRepository;
    private final StockReceiptItemRepository stockReceiptItemRepository;
    private final ProductOptionRepository productOptionRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class, ResponseStatusException.class})

    public HandleResponse<Void> handle(DeleteStockReceiptCommand command) {
        var exist = stockReceiptRepository.findById(command.stockReceiptId());
        if (exist.isEmpty()) {
            return HandleResponse.error("Không tìm thấy phiếu nhập kho với id " + command.stockReceiptId());
        }
        var stockReceipt = exist.get();
        for (StockReceiptItem item : stockReceipt.getStockReceiptItems()) {
            var productOption = item.getProductOption();
            productOption.setStock(productOption.getStock() - item.getQuantity());
            if (productOption.getStock() < 0) {
                throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Số lượng tồn kho không được nhỏ hơn 0: sản phẩm " + productOption.getProduct().getName());
            }
            productOptionRepository.save(productOption);
            stockReceiptItemRepository.delete(item);
        }
        stockReceiptRepository.delete(stockReceipt);
        return HandleResponse.ok();

    }
}
