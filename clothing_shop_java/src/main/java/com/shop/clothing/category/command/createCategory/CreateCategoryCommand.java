package com.shop.clothing.category.command.createCategory;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateCategoryCommand implements IRequest<Integer> {
    @NotEmpty(message = "Tên danh mục không được để trống")
    @Length(min = 1, max = 50, message = "Tên danh mục phải từ 1 đến 50 ký tự")
    private String name;

    private int parentId = 0;
}
