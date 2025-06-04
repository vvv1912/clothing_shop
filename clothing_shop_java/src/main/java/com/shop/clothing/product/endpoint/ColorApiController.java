package com.shop.clothing.product.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.product.command.createColor.CreateColorCommand;

import com.shop.clothing.product.dto.ColorDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/color")
@AllArgsConstructor
public class ColorApiController {
    private final ISender sender;


    @PostMapping("/create")
    public ResponseEntity<Integer> createColor(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid CreateColorCommand createColorCommand) {
        var result = sender.send(createColorCommand);
        return ResponseEntity.ok(result.orThrow());
    }
    @GetMapping()
    public ResponseEntity<Collection<ColorDto>> getAllColors() {
        var result = sender.send(new com.shop.clothing.product.query.getAllColors.GetAllColorQuery()).orThrow();
        return ResponseEntity.ok(result);
    }
}
