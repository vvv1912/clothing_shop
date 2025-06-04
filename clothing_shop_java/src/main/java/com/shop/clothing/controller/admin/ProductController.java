package com.shop.clothing.controller.admin;

import com.shop.clothing.category.query.getAllCategories.GetAllCategoriesQueries;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.product.command.deleteProduct.DeleteProductCommand;
import com.shop.clothing.product.query.getAllColors.GetAllColorQuery;
import com.shop.clothing.product.query.getAllProducts.GetAllProductsQuery;
import com.shop.clothing.product.query.getDeletedProductOptionsByProductId.GetDeletedProductOptionsByProductId;
import com.shop.clothing.product.query.getProductById.GetProductByIdQuery;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/product")
@Secured("PRODUCT_MANAGEMENT")
public class ProductController {
    private final ISender sender;

    @GetMapping()
    public String getProducts(Model model, GetAllProductsQuery getAllProductsQuery) {
        var allProducts = sender.send(getAllProductsQuery).get();
        var query = new GetAllCategoriesQueries();
        query.setPageSize(1000);
        query.setSortDir("asc");
        query.setSortField("name");
        var categories = sender.send(query).get();
        model.addAttribute("categories", categories.getData());
        model.addAttribute("products", allProducts);
        return "admin/product/index";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        var query = new GetAllCategoriesQueries();
        query.setPageSize(1000);
        query.setSortDir("asc");
        query.setSortField("name");
        var categories = sender.send(query).get();
        model.addAttribute("categories", categories.getData());
        return "admin/product/create";
    }

    @GetMapping("{id}/view")

    public String viewProduct(Model model, @PathVariable int id) {

        var query = new GetProductByIdQuery(id);
        var product = sender.send(query).orThrow();
        var deletedProductOptions = sender.send(new GetDeletedProductOptionsByProductId(id)).get();
        product.getProductOptions().addAll(deletedProductOptions);
        var colors = sender.send(new GetAllColorQuery()).get();
        product.getProductOptions().sort((a, b) -> {
            if (a.getColor().getName().equals(b.getColor().getName())) {
                return a.getSize().compareTo(b.getSize());
            }
            return a.getColor().getName().compareTo(b.getColor().getName());
        });
        model.addAttribute("product", product);
        model.addAttribute("colors", colors);
        return "admin/product/view";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('PRODUCT_MANAGEMENT')")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        var command = new DeleteProductCommand(id);
        sender.send(command);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/edit")
    @PreAuthorize("hasAnyAuthority('PRODUCT_MANAGEMENT')")
    public String editProduct(Model model, @PathVariable int id) {
        var query = new GetAllCategoriesQueries();
        var product = sender.send(new GetProductByIdQuery(id)).get();
        query.setPageSize(200);
        query.setSortDir("asc");
        query.setSortField("name");
        var categories = sender.send(query).get();
        model.addAttribute("categories", categories.getData());
        model.addAttribute("product", product);
        return "admin/product/edit";
    }

//    @GetMapping("{id}/options/create")
//    @PreAuthorize("hasAnyAuthority('PRODUCT_MANAGEMENT')")
//    public String createProductOption(Model model, @PathVariable String id) {
//        return "admin/product/option/create";
//    }
//
//    @GetMapping("options/{id}/view")
//    @PreAuthorize("hasAnyAuthority('PRODUCT_MANAGEMENT')")
//    public String viewProductOptions(Model model, @PathVariable String id) {
//        return "admin/product/option/view";
//    }
}
