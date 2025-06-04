package com.shop.clothing.product.query.getDeletedProductOptionsByProductId;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.product.dto.ProductOptionDto;
import com.shop.clothing.product.entity.ProductOption;

import java.util.List;

public record GetDeletedProductOptionsByProductId(int productId) implements IRequest<List<ProductOptionDto>> {
}
