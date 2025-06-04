package com.shop.clothing.category.endpoint;

import com.shop.clothing.category.CategoryBriefDto;
import com.shop.clothing.category.command.createCategory.CreateCategoryCommand;
import com.shop.clothing.category.command.deleteCategory.DeleteCategoryCommand;
import com.shop.clothing.category.command.updateCategory.UpdateCategoryCommand;
import com.shop.clothing.category.query.getAllCategories.GetAllCategoriesQueries;
import com.shop.clothing.category.query.getAllCategoriesGroupByParentQuery.GetAllCategoriesGroupByParentQuery;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.dto.Paginated;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController()
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryApiController {
    private final ISender sender;

    @GetMapping()
    public Paginated<CategoryBriefDto> getCategories(@Valid @ParameterObject GetAllCategoriesQueries paginationRequest) {
        return sender.send(paginationRequest).get();
    }

    @PostMapping("/create")
    @PostAuthorize("hasAuthority('CATEGORY_MANAGEMENT')")
    public ResponseEntity<Integer> createCategory(@Valid @RequestBody CreateCategoryCommand createCategoryRequest) {
        var result = sender.send(createCategoryRequest);
        return ResponseEntity.ok(result.orThrow());

    }

    @PutMapping("/update")
    @PostAuthorize("hasAuthority('CATEGORY_MANAGEMENT')")
    @Secured("CATEGORY_MANAGEMENT")
    public ResponseEntity<Boolean> createCategory(@Valid @RequestBody UpdateCategoryCommand updateCategoryRequest) {
        var result = sender.send(updateCategoryRequest);
        return ResponseEntity.ok(result.orThrow());

    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @PostAuthorize("hasAuthority('CATEGORY_MANAGEMENT')")
    public void deleteCategory(@PathVariable String id) {
        if (id.isBlank()) throw new IllegalArgumentException("id is null");
        var result = sender.send(new DeleteCategoryCommand(Integer.parseInt(id)));
        ResponseEntity.ok(result.orThrow());
    }

    @GetMapping("/getAllCategoriesGroupByParent")
    public ResponseEntity<Object> getAllCategoriesGroupByParent() {
        var result = sender.send(new GetAllCategoriesGroupByParentQuery());
        return ResponseEntity.ok(result.orThrow());
    }
}
