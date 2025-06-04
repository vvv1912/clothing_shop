package com.shop.clothing.auth.commands.forgotPassword;

import com.shop.clothing.auth.JWT.JwtService;
import com.shop.clothing.auth.repository.IUserRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.config.AppProperties;
import com.shop.clothing.mail.MailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class ForgotPasswordCommandHandler implements IRequestHandler<ForgotPasswordCommand, Void> {
    private final JwtService jwtService;
    private final IUserRepository IUserRepository;
    private final MailService mailService;
    private final AppProperties appProperties;

    @Override
    public HandleResponse<Void> handle(ForgotPasswordCommand forgotPasswordCommand) {
        var user = IUserRepository.findByEmail(forgotPasswordCommand.email());
        if (user.isEmpty()) {
            return HandleResponse.error("Email không tồn tại");
        }
        if (!user.get().isAccountEnabled()) {
            return HandleResponse.error("Tài khoản đã bị khóa");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.get().getEmail());
        claims.put("userId", user.get().getUserId());
        //for 15 minutes
        String token = jwtService.generateToken(claims, 15 * 60 * 1000);
        var to = user.get().getEmail();
        var subject = "Reset password";
        var url = appProperties.getHost() + "/auth/reset-password?token=" + token;
        var content = "Click vào link sau để reset password: " + url;
        CompletableFuture.runAsync(() -> {
            try {
                mailService.sendEmail(to, subject, content);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        return HandleResponse.ok();

    }
}
