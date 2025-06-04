package com.shop.clothing.controller.shop;

import com.shop.clothing.auth.JWT.JwtService;
import com.shop.clothing.auth.commands.forgotPassword.ForgotPasswordCommand;
import com.shop.clothing.auth.commands.login.LoginRequest;
import com.shop.clothing.auth.commands.register.RegisterCommand;
import com.shop.clothing.auth.commands.resetPassword.ResetPasswordCommand;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.dto.NotificationDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final ISender sender;
    private final JwtService jwtService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerCommand", RegisterCommand.builder().build());
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model, Authentication authentication) {
        model.addAttribute("loginRequest", LoginRequest.builder().build());
        if (authentication != null) {
            var redirectUrl = "redirect:/?" + NotificationDto.builder()
                    .title("Đăng nhập thành công")
                    .type("success")
                    .content("")
                    .build().toParams();

            return redirectUrl;
        }
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String createForgotPassword(@RequestParam String email, Model model) {
        var command = new ForgotPasswordCommand(email);
        var result = sender.send(command);
        if (result.isOk()) {
            model.addAttribute("success", true);
            return "forgot-password";
        }
        model.addAttribute("error", result.getError());
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam String token, Model model) {
        try {
            var email = jwtService.getValue(token, c -> c.get("email", String.class));
        } catch (Exception e) {
            model.addAttribute("error", "Token không hợp lệ");
            return "forgot-password";
        }
        var command = new ResetPasswordCommand();
        command.setToken(token);
        model.addAttribute("resetPasswordCommand", command);

        return "reset-password";
    }


    @PostMapping("/reset-password")
    public String changePassword(@Valid ResetPasswordCommand resetPasswordCommand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "reset-password";
        }
        var result = sender.send(resetPasswordCommand);
        if(result.hasError()){
            model.addAttribute("error", result.getError());
            return "reset-password";
        }

        return "redirect:/auth/login";
    }

    @GetMapping("/reset-password/success")
    public String resetPasswordSuccess() {
        return "reset-password-success";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterCommand registerCommand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        var result = sender.send(registerCommand);
        if (result.isOk()) {
            model.addAttribute("notification", NotificationDto.builder()
                    .title("Đăng ký thành công")
                    .content("Vui lòng kiểm tra email để kích hoạt tài khoản")
                    .build());
            // redirect to login page
            return "redirect:/auth/login";
        }
        bindingResult.addError(new ObjectError("registerCommand", result.getError()));
        return "register";
    }
}
