package com.shop.clothing.cart.command.removeItems;

import com.shop.clothing.common.Cqrs.IRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class RemoveItemsInCartCommand implements IRequest<Void> {
    public List<Integer> productOptionIds = new ArrayList<>();
}
