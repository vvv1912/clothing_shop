package com.shop.clothing.rating.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.config.ICurrentUserService;
import com.shop.clothing.rating.command.createRating.CreateRatingCommand;
import com.shop.clothing.rating.command.updateRating.UpdateRatingCommand;
import com.shop.clothing.rating.dto.RatingDto;
import com.shop.clothing.rating.query.getAllRatingOfProductId.GetAllRatingOfProductIdQuery;
import com.shop.clothing.rating.query.getRatingOfMyOrderItem.GetMyRatingOfMyOrderItemQuery;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rating")
public class RatingApiController {
    private final ISender sender;
    private ICurrentUserService currentUserService;

    @GetMapping()
    public ResponseEntity<Paginated<RatingDto>> getAllRatingOfProduct(@ParameterObject GetAllRatingOfProductIdQuery query) {
        var result = sender.send(query);
        return ResponseEntity.ok(result.orThrow());
    }

    @GetMapping("/my-rating")
    @Secured("CAN_ORDER")
    public ResponseEntity<RatingDto> getMyRatingOfProduct(@ParameterObject GetMyRatingOfMyOrderItemQuery query) {
        query.setUserId(currentUserService.getCurrentUserId().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")));
        var result = sender.send(query);
        return ResponseEntity.ok(result.orThrow());
    }

    @PostMapping()
    @Secured("CAN_ORDER")
    public ResponseEntity<Integer> createRating(@RequestBody @Valid CreateRatingCommand command) {
        var result = sender.send(command);
        return ResponseEntity.ok(result.orThrow());
    }

    @PutMapping()
    @Secured("CAN_ORDER")
    public ResponseEntity<Void> updateRating(@RequestBody @Valid UpdateRatingCommand command) {
        var result = sender.send(command);
        return ResponseEntity.ok(result.orThrow());
    }
}
