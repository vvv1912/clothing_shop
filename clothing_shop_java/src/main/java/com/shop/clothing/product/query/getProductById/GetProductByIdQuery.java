package com.shop.clothing.product.query.getProductById;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.product.dto.ProductDetailDto;


public record GetProductByIdQuery(int id) implements IRequest<ProductDetailDto> {

}
