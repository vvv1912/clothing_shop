package com.shop.clothing.stockReceipt.command.supplier.updateSupplier;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.stockReceipt.entity.Supplier;
import com.shop.clothing.stockReceipt.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UpdateSupplierCommandHandler implements IRequestHandler<UpdateSupplierCommand, Void> {
    private final SupplierRepository supplierRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public HandleResponse<Void> handle(UpdateSupplierCommand command) throws Exception {
        var exist = supplierRepository.findById(command.getSupplierId());
        if (exist.isEmpty()) {
            return HandleResponse.error("Không tìm thấy nhà cung cấp");
        }
        var supplier = exist.get();
        supplier.setName(command.getName());
        supplier.setAddress(command.getAddress());
        supplier.setEmail(command.getEmail());
        supplier.setPhone(command.getPhone());
        supplier.setDescription(command.getDescription());
        supplierRepository.save(supplier);
        return HandleResponse.ok();

    }
}
