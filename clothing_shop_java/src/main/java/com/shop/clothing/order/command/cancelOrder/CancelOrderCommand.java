package com.shop.clothing.order.command.cancelOrder;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CancelOrderCommand implements IRequest<Boolean> {
    @NotEmpty(message = "Mã đơn hàng không được để trống")
   private String orderId ;
   private String reason ;
}
