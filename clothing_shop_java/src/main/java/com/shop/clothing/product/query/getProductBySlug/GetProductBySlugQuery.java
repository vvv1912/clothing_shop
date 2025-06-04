package com.shop.clothing.product.query.getProductBySlug;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.dto.ProductDetailDto;
import lombok.Getter;
import lombok.Setter;


public record GetProductBySlugQuery(String slug) implements IRequest<ProductDetailDto> {

}
