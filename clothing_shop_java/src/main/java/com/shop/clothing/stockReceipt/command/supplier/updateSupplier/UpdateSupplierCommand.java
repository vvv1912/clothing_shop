package com.shop.clothing.stockReceipt.command.supplier.updateSupplier;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSupplierCommand implements IRequest<Void> {
    @Min(value = 1, message = "Mã nhà cung cấp không hợp lệ")
    private int supplierId;
    @NotEmpty(message = "Tên nhà cung cấp không được để trống")
    private String name;
    @NotEmpty(message = "Địa chỉ không được để trống")
    private String address;
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Số điện thoại không hợp lệ")
    private String phone;
    @Email(message = "Email không hợp lệ")
    private String email;
    private String description;

}
