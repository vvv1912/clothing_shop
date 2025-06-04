package com.shop.clothing.category.query.getCategoryById;

import com.shop.clothing.category.CategoryDetailDto;
import com.shop.clothing.category.CategoryRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetCategoryByIdQueryHandler implements IRequestHandler<GetCategoryByIdQuery, CategoryDetailDto> {
    private final CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Override
    public HandleResponse<CategoryDetailDto> handle(GetCategoryByIdQuery getCategoryByIdQuery) {
        var category = categoryRepository.findById(getCategoryByIdQuery.id());
        if (category.isEmpty()) {
            return HandleResponse.ok(null);
        }
        return HandleResponse.ok(modelMapper.map(category.get(), CategoryDetailDto.class));
    }
}
