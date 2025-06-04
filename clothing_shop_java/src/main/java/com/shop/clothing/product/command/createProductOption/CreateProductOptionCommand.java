package com.shop.clothing.product.command.createProductOption;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductOptionCommand implements IRequest<Integer> {
    @NotNull(message = "Màu không được để trống")
    @NotEmpty(message = "Màu không được để trống")
    private String colorName;
    @NotEmpty(message = "Size không được để trống")
    @NotNull(message = "Size không được để trống")
    private String size;
    private  int stock=0;
    @Min(value = 1, message = "Sản phẩm không hợp lệ")
    @NotNull(message = "Sản phẩm không được để trống")
    private int productId;
}
