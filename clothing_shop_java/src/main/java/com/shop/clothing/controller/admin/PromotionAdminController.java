package com.shop.clothing.controller.admin;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.promotion.query.checkPromotion.CheckPromotionQuery;
import com.shop.clothing.promotion.query.getPromotionById.GetPromotionByIdQuery;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/promotion")
@AllArgsConstructor
@Controller
@Secured("PRODUCT_MANAGEMENT")

public class PromotionAdminController {
    private final ISender sender;

    @GetMapping
    public String index() {
        return "admin/promotion/index";
    }
    @GetMapping("/{id}")
    public String detail(@PathVariable String id, Model model) {
        var promotion = sender.send(new GetPromotionByIdQuery(Integer.parseInt(id))).orThrow();
        model.addAttribute("promotion", promotion);
        return "admin/promotion/detail";
    }
    @GetMapping("/create")
    public String create() {
        return "admin/promotion/create";
    }
}
