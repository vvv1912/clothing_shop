package com.shop.clothing.promotion.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.promotion.PromotionDto;
import com.shop.clothing.promotion.command.createPromotion.CreatePromotionCommand;
import com.shop.clothing.promotion.command.updatePromotion.UpdatePromotionCommand;
import com.shop.clothing.promotion.query.checkPromotion.CheckPromotionQuery;
import com.shop.clothing.promotion.query.getAllPromotions.GetAllPromotionQuery;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotion")
@AllArgsConstructor

public class PromotionApiController {
    private final ISender sender;
    @GetMapping("/check")
    public ResponseEntity<PromotionDto> checkPromotion(@Valid @ParameterObject CheckPromotionQuery checkPromotionQuery) {
       var result = sender.send(checkPromotionQuery);
         return ResponseEntity.ok(result.orThrow());
    }
    @GetMapping()
    public ResponseEntity<Paginated<PromotionDto>> getAllPromotions(@Valid @ParameterObject GetAllPromotionQuery query) {
       var result = sender.send(query);
         return ResponseEntity.ok(result.orThrow());
    }
    @PostMapping()
    public ResponseEntity<Integer> createPromotion(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreatePromotionCommand command) {
       var result = sender.send(command);
         return ResponseEntity.ok(result.orThrow());
    }

    @PutMapping()
    public ResponseEntity<Void> updatePromotion(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid UpdatePromotionCommand command) {
       var result = sender.send(command).orThrow();
         return ResponseEntity.ok().build();
    }

    @PatchMapping("/{promotionId}/toggleDisable")
    public ResponseEntity<Void> toggleDisablePromotion(@PathVariable Integer promotionId) {
       var result = sender.send(new com.shop.clothing.promotion.command.toggleDisablePromotion.ToggleDisablePromotion(promotionId));
         return ResponseEntity.ok(result.orThrow());
    }

    @DeleteMapping("/{promotionId}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> tryDeletePromotion(@PathVariable Integer promotionId) {
       var result = sender.send(new com.shop.clothing.promotion.command.tryDeletePromotion.TryDeletePromotionCommand(promotionId)).orThrow();
         return ResponseEntity.noContent().build();
    }

}
