package com.shop.clothing.controller.shop;

import com.shop.clothing.cart.query.getMyCart.GetMyCartQuery;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.order.query.getMyOrders.GetMyOrderQuery;
import com.shop.clothing.user.query.getMyProfile.GetMyProfileQuery;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final ISender _sender;

    @GetMapping("my-account")
    public ModelAndView myProfile() {
        var result = _sender.send(new GetMyProfileQuery());
        if (result.hasError()) {
            return new ModelAndView("redirect:/auth/login");
        }
        return new ModelAndView("user/my-account", "user", result.get());
    }

    @GetMapping("my-cart")
    @PreAuthorize("hasAuthority('CAN_ORDER')")
    public ModelAndView myCart() {
        var result = _sender.send(new GetMyCartQuery());
        if (result.hasError()) {
            return new ModelAndView("redirect:/auth/login");
        }
        return new ModelAndView("user/my-cart", "cartItems", result.orThrow());
    }

    @GetMapping("my-order")
    @PreAuthorize("hasAuthority('CAN_ORDER')")
    public ModelAndView myOrder() {
        var result = _sender.send(new GetMyOrderQuery());
        return new ModelAndView("user/my-order", "orders", result.orThrow());

    }
}
