package com.shop.clothing.product.query.getAllColors;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.product.dto.ColorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;


public record GetAllColorQuery() implements IRequest<Collection<ColorDto>> {

}
