package com.shop.clothing.controller.admin;

import com.shop.clothing.common.Cqrs.ISender;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/report")
@Secured("REPORT_MANAGEMENT")

public class ReportController {
    private final ISender sender;

    @GetMapping("/revenue")
    public String getRevenueReport() {
        return "admin/report/revenue";
    }

    @GetMapping("/product")
    public String getProductReport() {
        return "admin/report/product";
    }

    @GetMapping("/category")
    public String getCategoryReport() {
        return "admin/report/category";
    }
}
