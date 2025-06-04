package com.shop.clothing.controller.admin;

import com.shop.clothing.common.Cqrs.ISender;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/order")
@Secured("ORDER_MANAGEMENT")

public class OrderAdminController {
    private final ISender _sender;

    @GetMapping()
    public String getOrders() {
        return "admin/order/index";
    }

    @GetMapping("/{id}")
    public String getOrderDetail(@PathVariable String id, Model model) {
        var query = new com.shop.clothing.order.query.getOrderById.GetOrderByIdQuery(id);
        var response = _sender.send(query);
        model.addAttribute("order", response.orThrow());
        return "admin/order/detail";
    }
}
