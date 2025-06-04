package com.shop.clothing.category.command.updateCategory;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCategoryCommand implements IRequest<Boolean> {
    @Min(1)
    @NotNull(message = "Id không được để trống")
    private int id;
    @NotEmpty(message = "Tên danh mục không được để trống")
    @Length(min = 3, max = 50, message = "Tên danh mục phải từ 3 đến 50 ký tự")
    private String name;



    private  int parentId = 0;
}
