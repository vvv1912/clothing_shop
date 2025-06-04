package com.shop.clothing.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/role")
@Secured("ROLE_MANAGEMENT")
public class RoleController {
    @GetMapping()
    public String getRoles() {
        return "admin/role/index";
    }
}
