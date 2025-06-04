package com.shop.clothing.controller.admin;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.stockReceipt.query.getAllSuppliers.GetAllSuppliersQuery;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/supplier")
@Secured("SUPPLIER_MANAGEMENT")
public class SupplierController {
    private final ISender sender;

    @GetMapping()
    public String index(Model model) {

        return "admin/supplier/index";
    }
}
