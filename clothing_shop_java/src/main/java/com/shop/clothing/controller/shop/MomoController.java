package com.shop.clothing.controller.shop;

import com.shop.clothing.payment.entity.enums.PaymentStatus;
import com.shop.clothing.payment.momo.MomoCallbackParam;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("/momo")
public class MomoController {
    private final com.shop.clothing.payment.momo.MomoService momoService;

    @GetMapping("/callback")
    public String callback(MomoCallbackParam param, Model model) {
        var payment = momoService.handleCallback(param);
        model.addAttribute("detailUrl", "/order/" + payment.getOrder().getOrderId());
        if (payment.getStatus() == PaymentStatus.PAID) {
            model.addAttribute("message", "Đơn hàng #" + payment.getOrder().getOrderId() + " đã được thanh toán thành công");
            model.addAttribute("header", "Thanh toán thành công");
            model.addAttribute("subMessage", "Cảm ơn bạn đã mua hàng tại shop");

            return "order/success";
        }
        model.addAttribute("message", "Thanh toán thất bại cho đơn hàng #" + payment.getOrder().getOrderId());
        model.addAttribute("header", "Thanh toán thất bại");
        model.addAttribute("subMessage", "Có lỗi xảy ra trong quá trình thanh toán");
        return "order/fail";
    }

}
