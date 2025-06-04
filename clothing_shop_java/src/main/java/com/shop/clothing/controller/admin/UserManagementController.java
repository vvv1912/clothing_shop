package com.shop.clothing.controller.admin;

import com.shop.clothing.auth.query.role.getAllRoles.GetAllRolesQuery;
import com.shop.clothing.category.command.updateCategory.UpdateCategoryCommand;
import com.shop.clothing.category.query.getAllCategories.GetAllCategoriesQueries;
import com.shop.clothing.common.Cqrs.ISender;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/admin/user")
@Controller
@Secured("USER_MANAGEMENT")
public class UserManagementController {
    private final ISender sender;

    @GetMapping()
    public String index(Model model) {
        var roles= sender.send(new  GetAllRolesQuery    ()).orThrow();

        model.addAttribute("roles", roles);
        return "admin/user/index";
    }
}
