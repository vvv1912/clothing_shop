package com.shop.clothing.controller.shop;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.product.query.advanceSearchProduct.AdvanceSearchAllProductsQuery;
import com.shop.clothing.product.query.getAllProducts.GetAllProductsQuery;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/")
@Controller
@AllArgsConstructor
public class Home {
    private final ISender sender;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/test")
    public String test() {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "User not found");
    }

    @GetMapping("/home")
    public String home(Model model) {
        var newProductQuery = new AdvanceSearchAllProductsQuery();
        newProductQuery.setSortField("product.createdDate");
        newProductQuery.setSortDir("desc");
        newProductQuery.setPage(1);
        newProductQuery.setPageSize(6);
        var hotProductQuery = new AdvanceSearchAllProductsQuery();
        hotProductQuery.setSortField("product.totalSold");
        hotProductQuery.setSortDir("desc");
        hotProductQuery.setPage(1);
        hotProductQuery.setPageSize(6);
        var newProducts = sender.send(newProductQuery).get();
        var hotProducts = sender.send(hotProductQuery).get();
        model.addAttribute("newProducts", newProducts.getData());
        model.addAttribute("hotProducts", hotProducts.getData());
        return "index";
    }
}
