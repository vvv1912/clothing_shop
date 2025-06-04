package com.shop.clothing.product.endpoint;


import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.product.command.createAndGetProductOption.CreateAndGetProductOptionCommand;
import com.shop.clothing.product.command.createProductOption.CreateProductOptionCommand;

import com.shop.clothing.product.command.recoveryProduct.RecoveryProductCommand;
import com.shop.clothing.product.command.recoveryProductOption.RecoveryProductOptionCommand;
import com.shop.clothing.product.dto.ProductOptionDetailDto;
import com.shop.clothing.product.query.getAllSizes.GetAllSizesQuery;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/product-option")
@AllArgsConstructor
public class ProductOptionApiController {
    private final ISender sender;

    @Secured("PRODUCT_MANAGEMENT")
    @PostMapping("/create")
    public ResponseEntity<Integer> createProductOption(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid CreateProductOptionCommand createProductOptionCommand) {
        var result = sender.send(createProductOptionCommand);
        return ResponseEntity.ok(result.orThrow());
    }

    @Secured("STOCK_RECEIPT_MANAGEMENT")
    @PostMapping("/createIfNotExist")
    public ResponseEntity<ProductOptionDetailDto> createProductOptionIfNotExist(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid CreateAndGetProductOptionCommand command) {
        var result = sender.send(command);
        return ResponseEntity.ok(result.orThrow());
    }

    @Secured("PRODUCT_MANAGEMENT")

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProductOption(@PathVariable @Parameter(in = ParameterIn.PATH, name = "id") int id) {
        var result = sender.send(new com.shop.clothing.product.command.deleteProductOption.DeleteProductOptionCommand(id));
        result.orThrow();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sizes")
    public ResponseEntity<Collection<String>> getAllSizes() {
        var sizes = sender.send(new GetAllSizesQuery());
        return ResponseEntity.ok(sizes.orThrow());
    }
    @PatchMapping("/recovery/{productOptionId}")
    public ResponseEntity<Void> recoveryProductOption(@PathVariable int productOptionId) {
        sender.send(new RecoveryProductOptionCommand(productOptionId)).orThrow();
        return ResponseEntity.ok().build();
    }
}
