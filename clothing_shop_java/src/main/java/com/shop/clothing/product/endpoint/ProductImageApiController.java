package com.shop.clothing.product.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.product.command.createProductImage.CreateProductImageCommand;
import com.shop.clothing.product.command.createProductOption.CreateProductOptionCommand;
import com.shop.clothing.product.command.deleteProductImage.DeleteProductImageCommand;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-image")
@AllArgsConstructor
public class ProductImageApiController {
    private final ISender sender;

    @Secured("PRODUCT_MANAGEMENT")
    @PostMapping("/create")
    public ResponseEntity<Void> createProductImage(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid CreateProductImageCommand createProductImageCommand) {
        var result = sender.send(createProductImageCommand);
        return ResponseEntity.ok().build();
    }
    @Secured("PRODUCT_MANAGEMENT")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProductImage(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid DeleteProductImageCommand command) {
        var result = sender.send(command);
        return ResponseEntity.ok().build();
    }
}
