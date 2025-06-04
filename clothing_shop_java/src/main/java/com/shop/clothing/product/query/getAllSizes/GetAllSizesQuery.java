package com.shop.clothing.product.query.getAllSizes;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.product.dto.ColorDto;

import java.util.Collection;


public record GetAllSizesQuery() implements IRequest<Collection<String>> {

}
