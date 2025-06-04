package com.shop.clothing.product.command.createAndGetProductOption;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.PaginationRequest;
import com.shop.clothing.product.dto.ProductOptionDetailDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CreateAndGetProductOptionCommand implements IRequest<ProductOptionDetailDto> {
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
