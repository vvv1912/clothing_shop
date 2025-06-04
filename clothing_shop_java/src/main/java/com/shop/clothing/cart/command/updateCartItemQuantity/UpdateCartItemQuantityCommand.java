package com.shop.clothing.cart.command.updateCartItemQuantity;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


public record UpdateCartItemQuantityCommand(int productOptionId,    @Min(value = 1, message = "Số lượng phải lớn hơn 0") int newQuantity)implements IRequest<Void>{
}
