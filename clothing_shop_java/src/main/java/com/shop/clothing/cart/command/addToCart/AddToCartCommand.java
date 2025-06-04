package com.shop.clothing.cart.command.addToCart;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddToCartCommand implements IRequest<Void> {


    private int productOptionId;
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int quantity;
}
