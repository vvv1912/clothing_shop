package com.shop.clothing.delivery.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.delivery.dto.GetValidShipServiceResponse;
import com.shop.clothing.delivery.query.getDeliveryFee.GetDeliveryFeeQuery;
import com.shop.clothing.delivery.query.getDeliveryOption.GetDeliveryOptionQuery;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
@AllArgsConstructor
public class DeliveryController {
    private final ISender sender;

    @PostMapping("/fee")
    public ResponseEntity<Integer> getDeliveryFee(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody GetDeliveryFeeQuery getDeliveryFeeQuery) {
        var result = sender.send(getDeliveryFeeQuery);
        return ResponseEntity.ok(result.orThrow());
    }
    @PostMapping("/option")
    public ResponseEntity<List<GetValidShipServiceResponse>> getDeliveryOption(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody GetDeliveryOptionQuery query) {
        var result = sender.send(query);
        return ResponseEntity.ok(result.orThrow());

    }
}
