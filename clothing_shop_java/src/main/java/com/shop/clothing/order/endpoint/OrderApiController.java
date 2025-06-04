package com.shop.clothing.order.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.order.command.cancelOrder.CancelOrderCommand;
import com.shop.clothing.order.command.createOrder.CreateOrderCommand;
import com.shop.clothing.order.command.updateStatus.UpdateOrderStatusCommand;
import com.shop.clothing.order.dto.OrderBriefDto;
import com.shop.clothing.order.query.getAllOrders.GetAllOrderQuery;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderApiController {
    private final ISender sender;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateOrderCommand createOrderCommand) {
        var result = sender.send(createOrderCommand);
        return ResponseEntity.ok(result.orThrow());
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ORDER_MANAGEMENT')")
    public ResponseEntity<Paginated<OrderBriefDto>> getAllOrders(@Valid @ParameterObject GetAllOrderQuery getAllOrderQuery) {
        System.err.println("================================");
        return ResponseEntity.ok(sender.send(getAllOrderQuery).orThrow());
    }

    @PatchMapping("/updateStatus")
    @PreAuthorize("hasAuthority('ORDER_MANAGEMENT')")
    public ResponseEntity<Void> updateOrderStatus(@Valid @RequestBody UpdateOrderStatusCommand updateOrderStatusCommand) {
        var result = sender.send(updateOrderStatusCommand);
        return ResponseEntity.ok(result.orThrow());
    }

    @PatchMapping("/cancel")
    @PreAuthorize("hasAuthority('CAN_ORDER')")
    public ResponseEntity<Boolean> cancelOrder(@RequestBody CancelOrderCommand command) {
        var result = sender.send(command);
        return ResponseEntity.ok(result.orThrow());
    }

}
