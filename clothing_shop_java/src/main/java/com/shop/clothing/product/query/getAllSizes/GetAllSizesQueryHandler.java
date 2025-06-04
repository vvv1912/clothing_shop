package com.shop.clothing.product.query.getAllSizes;


import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.dto.ColorDto;
import com.shop.clothing.product.entity.ProductOption;
import com.shop.clothing.product.repository.ColorRepository;
import com.shop.clothing.product.repository.ProductOptionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service

public class GetAllSizesQueryHandler  implements IRequestHandler<GetAllSizesQuery,Collection<String>> {
   private final ProductOptionRepository productOptionRepository;

    @Override
    public HandleResponse<Collection<String>> handle(GetAllSizesQuery getAllSizesQuery) throws Exception {
        return HandleResponse.ok(productOptionRepository.getAllSizes());
    }
}
