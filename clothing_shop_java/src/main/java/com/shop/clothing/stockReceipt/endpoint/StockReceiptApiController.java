package com.shop.clothing.stockReceipt.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.stockReceipt.command.stockReceipt.createStockReceipt.CreateStockReceiptCommand;
import com.shop.clothing.stockReceipt.command.stockReceipt.deleteStockReceipt.DeleteStockReceiptCommand;
import com.shop.clothing.stockReceipt.dto.StockReceiptBriefDto;
import com.shop.clothing.stockReceipt.dto.StockReceiptDetailDto;
import com.shop.clothing.stockReceipt.query.getAllStockReceipts.GetAllStockReceiptsQuery;
import com.shop.clothing.stockReceipt.query.getById.GetStockReceiptByIdQuery;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock-receipt")
@AllArgsConstructor
@Secured("STOCK_RECEIPT_MANAGEMENT")
public class StockReceiptApiController {
    private final ISender sender;
    // delete and create receipt

    @PostMapping("/")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<Integer> createStockReceipt(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody CreateStockReceiptCommand command) throws Exception {
        var result = sender.send(command);
        return ResponseEntity.created(new java.net.URI("/api/stock-receipt/" + result.orThrow())).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteStockReceipt(@PathVariable int id) {
        var result = sender.send(new DeleteStockReceiptCommand(id)).orThrow();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<Paginated<StockReceiptBriefDto>> getAllStockReceipts(@ParameterObject GetAllStockReceiptsQuery query) {
        var result = sender.send(query).orThrow();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockReceiptDetailDto> getStockReceiptById(@PathVariable int id) {
        var result = sender.send(new GetStockReceiptByIdQuery(id)).orThrow();
        return ResponseEntity.ok(result);
    }
}
