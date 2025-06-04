package com.shop.clothing.order.query.getMyOrders;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.order.dto.OrderDto;

import java.util.List;

public record GetMyOrderQuery() implements IRequest<List<OrderDto>> {

}
