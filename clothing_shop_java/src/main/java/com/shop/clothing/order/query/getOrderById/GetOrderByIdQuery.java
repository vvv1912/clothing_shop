package com.shop.clothing.order.query.getOrderById;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.order.dto.OrderDetailDto;
import com.shop.clothing.order.dto.OrderDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public record GetOrderByIdQuery(String id) implements IRequest<OrderDetailDto> {

}
