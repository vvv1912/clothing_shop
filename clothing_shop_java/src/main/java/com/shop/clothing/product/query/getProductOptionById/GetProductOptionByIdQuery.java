package com.shop.clothing.product.query.getProductOptionById;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.product.dto.ProductOptionDetailDto;
import com.shop.clothing.product.dto.ProductOptionDto;
import com.shop.clothing.product.entity.ProductOption;



public record GetProductOptionByIdQuery(int id) implements IRequest<ProductOptionDetailDto> {
}
