package com.shop.clothing.controller.admin;


import com.shop.clothing.shop.ShopSetting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@RequestMapping("/admin/shop-setting")
@Controller
@Secured("SHOP_INFO_MANAGEMENT")
public class ShopSettingController {
    private final ShopSetting shopSetting;

    @GetMapping()
    public ModelAndView index() {
        return new ModelAndView("admin/shop-setting", "shopSetting", shopSetting);
    }
}
