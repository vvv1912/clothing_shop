package com.shop.clothing.cart.endpoint;

import com.shop.clothing.cart.command.addToCart.AddToCartCommand;
import com.shop.clothing.cart.command.clearCart.ClearCartCommand;
import com.shop.clothing.cart.command.removeItem.RemoveItemFromCartCommand;
import com.shop.clothing.cart.command.updateCartItemQuantity.UpdateCartItemQuantityCommand;
import com.shop.clothing.cart.dto.CartItemDto;
import com.shop.clothing.cart.query.getMyCart.GetMyCartQuery;
import com.shop.clothing.common.Cqrs.ISender;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
@Secured("CAN_ORDER")
public class CartApiController {
    private ISender sender;

    @GetMapping("/my-cart")
    public ResponseEntity<Collection<CartItemDto>> getMyCart() {
        return ResponseEntity.ok(sender.send(new GetMyCartQuery()).orThrow());
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<Void> addToCart(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody AddToCartCommand addToCartCommand) {
        sender.send(addToCartCommand).orThrow();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear-cart")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> clearCart() {
        sender.send(new ClearCartCommand()).orThrow();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-item-quantity")
    public ResponseEntity<Void> updateCartItemQuantity(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateCartItemQuantityCommand updateCartItemQuantityCommand) {
        sender.send(updateCartItemQuantityCommand).orThrow();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productOptionId}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removeItemFromCart(@PathVariable int productOptionId) {
        sender.send(new RemoveItemFromCartCommand(productOptionId)).orThrow();
        return ResponseEntity.noContent().build();
    }

}
