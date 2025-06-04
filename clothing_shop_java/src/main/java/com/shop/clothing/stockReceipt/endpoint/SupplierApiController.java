package com.shop.clothing.stockReceipt.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.dto.Paginated;

import com.shop.clothing.stockReceipt.command.supplier.createSupplier.CreateSupplierCommand;
import com.shop.clothing.stockReceipt.command.supplier.deleteSupplier.DeleteSupplierCommand;
import com.shop.clothing.stockReceipt.command.supplier.updateSupplier.UpdateSupplierCommand;
import com.shop.clothing.stockReceipt.dto.SupplierDto;
import com.shop.clothing.stockReceipt.query.getAllSuppliers.GetAllSuppliersQuery;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
@AllArgsConstructor
public class SupplierApiController {
    private final ISender sender;
    // delete and create receipt

    @PostMapping()
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<Integer> createSupplier(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateSupplierCommand command) throws Exception {
        var result = sender.send(command);
        return ResponseEntity.created(new java.net.URI("/api/supplier/" + result.orThrow())).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
        sender.send(new DeleteSupplierCommand(id)).orThrow();
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<Paginated<SupplierDto>> getAllSuppliers(@ParameterObject GetAllSuppliersQuery query) {
        var result = sender.send(query).orThrow();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSupplier(@PathVariable int id, @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateSupplierCommand command) {
        command.setSupplierId(id);
        sender.send(command).orThrow();
        return ResponseEntity.ok().build();
    }
}
