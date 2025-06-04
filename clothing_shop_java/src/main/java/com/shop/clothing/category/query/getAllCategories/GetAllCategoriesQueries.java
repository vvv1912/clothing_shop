package com.shop.clothing.category.query.getAllCategories;

import com.shop.clothing.category.CategoryBriefDto;
import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.PaginationRequest;
import com.shop.clothing.common.dto.Paginated;
import lombok.*;


@Getter
public class GetAllCategoriesQueries extends PaginationRequest implements IRequest<Paginated<CategoryBriefDto>> {
}