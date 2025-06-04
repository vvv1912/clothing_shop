package com.shop.clothing.controller.admin;

import com.shop.clothing.category.command.updateCategory.UpdateCategoryCommand;
import com.shop.clothing.category.query.getAllCategories.GetAllCategoriesQueries;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.category.command.createCategory.CreateCategoryCommand;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/category")

public class CategoryController {
    private final ISender sender;

    @GetMapping()
    @Secured("CATEGORY_MANAGEMENT")
    public String getCategories(Model model, CreateCategoryCommand createCategoryCommand) {
        var page = new GetAllCategoriesQueries();
        page.setPageSize(100);
        var allCategories = sender.send(page).get();
        model.addAttribute("categories", allCategories);
        model.addAttribute("createCategoryRequest", createCategoryCommand);
        model.addAttribute("updateCategoryRequest", new UpdateCategoryCommand());
        return "admin/category/index";
    }
}
