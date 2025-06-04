package com.shop.clothing.product.query.getProductOptionsByListId;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.product.dto.ProductOptionDetailDto;

import java.util.List;


public record GetProductOptionsByListIdQuery(List<Integer> ids) implements IRequest<List<ProductOptionDetailDto>> {
}
