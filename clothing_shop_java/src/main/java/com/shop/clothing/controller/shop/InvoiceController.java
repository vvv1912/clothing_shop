package com.shop.clothing.controller.shop;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.order.query.createInvoicePdf.CreateInvoicePdfQuery;
import com.shop.clothing.order.query.getOrderById.GetOrderByIdQuery;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/invoice")
@AllArgsConstructor

public class InvoiceController {
    private final ISender sender;
    @GetMapping("{id}/view")
    public String viewInvoice(@PathVariable String id, Model model) {
        System.out.println(id);
        var order = sender.send(new GetOrderByIdQuery(id));
        model.addAttribute("order", order.orThrow());
        return "invoice/index";
    }
    @GetMapping("{id}/download")
    public void downloadInvoice(@PathVariable String id, HttpServletResponse response) throws Exception {
        System.out.println(id);
        var result = sender.send(new CreateInvoicePdfQuery(id));
        var fileName = result.orThrow();
        var file = new java.io.File(fileName);
        response.setContentLength((int) file.length());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        org.springframework.util.FileCopyUtils.copy(new java.io.FileInputStream(file), response.getOutputStream());



    }
}
