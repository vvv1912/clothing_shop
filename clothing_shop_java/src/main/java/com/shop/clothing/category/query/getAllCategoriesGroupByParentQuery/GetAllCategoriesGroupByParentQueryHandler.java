package com.shop.clothing.category.query.getAllCategoriesGroupByParentQuery;

import com.shop.clothing.category.CategoryDetailDto;
import com.shop.clothing.category.CategoryRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class GetAllCategoriesGroupByParentQueryHandler implements IRequestHandler<GetAllCategoriesGroupByParentQuery, List<CategoryDetailDto>> {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public HandleResponse<List<CategoryDetailDto>> handle(GetAllCategoriesGroupByParentQuery getAllCategoriesGroupByParentQuery) throws Exception {
        var categories = categoryRepository.findAllByParentCategoryIdIsNull().stream().toList();
        var categoryDetailDtos = categories.stream().map(category -> {
            category.getChildren().forEach(child -> {
                child.setParent(null);
                child.getChildren().forEach(grandChild -> {
                    grandChild.setParent(null);
                });
            });
            var dto = modelMapper.map(category, CategoryDetailDto.class);
            dto.setParent(null);
            return dto;
        }).toList();
        return HandleResponse.ok(categoryDetailDtos);
    }
}
