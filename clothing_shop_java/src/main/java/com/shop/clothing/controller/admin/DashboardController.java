package com.shop.clothing.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
@Secured({"ADMIN_DASHBOARD"})
public class DashboardController {

        @GetMapping("/dashboard")
        public String dashboard() {
            return "admin/index";
        }
        @GetMapping("")
        public String index() {
            return "redirect:/admin/dashboard";
        }

}
