package com.shop.clothing.product.query.getAllColors;


import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.dto.ColorDto;
import com.shop.clothing.product.repository.ColorRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class GetAllColorQueryHandler  implements IRequestHandler<GetAllColorQuery, Collection<ColorDto>> {
   private final ColorRepository colorRepository;
   private final ModelMapper modelMapper;
    @Override
    public HandleResponse<Collection<ColorDto>> handle(GetAllColorQuery getAllColorQuery) throws Exception {
        var colors = colorRepository.findAll();
        var colorDtos = colors.stream().map(color -> modelMapper.map(color, ColorDto.class)).toList();
        return HandleResponse.ok(colorDtos);
    }
}
