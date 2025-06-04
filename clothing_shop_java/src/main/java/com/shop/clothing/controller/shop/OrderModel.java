package com.shop.clothing.controller.shop;


import com.shop.clothing.order.command.createOrder.CreateOrderCommand;
import com.shop.clothing.product.dto.ProductOptionDetailDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderModel {
    private List<ProductOptionDetailDto> orderItems;

    public void setOrderItems(List<ProductOptionDetailDto> orderItems, List<CreateOrderCommand.OrderItem> orderItemsWithQuantity) {
        this.orderItems = orderItems;
        for (ProductOptionDetailDto productOptionDetailDto : orderItems) {
            var orderItem = orderItemsWithQuantity.stream().filter(item -> item.getProductOptionId() == productOptionDetailDto.getProductOptionId()).findFirst().get();
            productOptionDetailDto.setQuantity(orderItem.getQuantity());
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (ProductOptionDetailDto productOptionDetailDto : orderItems) {
            totalPrice += productOptionDetailDto.getFinalPrice() * productOptionDetailDto.getQuantity();
        }
        return ((int) (totalPrice * 1.0 / 1000)) * 1000;
    }

    public String getVietnameseTotalPrice() {
        return String.format("%,dđ", getTotalPrice());
    }

}
