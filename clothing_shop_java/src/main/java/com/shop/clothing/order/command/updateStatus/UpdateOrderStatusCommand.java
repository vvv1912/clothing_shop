package com.shop.clothing.order.command.updateStatus;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.order.entity.enums.OrderStatus;

public record UpdateOrderStatusCommand(String orderId, OrderStatus status) implements IRequest<Void> {
}
