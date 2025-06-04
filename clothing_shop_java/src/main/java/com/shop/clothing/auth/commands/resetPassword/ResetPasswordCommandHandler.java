package com.shop.clothing.auth.commands.resetPassword;

import com.shop.clothing.auth.JWT.JwtService;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.SignatureException;

@Service
@AllArgsConstructor
public class ResetPasswordCommandHandler implements IRequestHandler<ResetPasswordCommand, Void> {
    private final UserDetailsPasswordService userDetailsService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailService;

    @Override
    public HandleResponse<Void> handle(ResetPasswordCommand resetPasswordCommand) throws Exception {
        String email;
        try {
            email = jwtService.getValue(resetPasswordCommand.getToken(), c -> c.get("email", String.class));
            if (email == null || email.isEmpty()) {
                return HandleResponse.error("Token không hợp lệ");
            }
        } catch (SignatureException e) {
            return HandleResponse.error("Token không hợp lệ");
        } catch (ExpiredJwtException e) {
            return HandleResponse.error("Token đã hết hạn, vui lòng thử lại");
        }
        var user = userDetailService.loadUserByUsername(email);
        userDetailsService.updatePassword(user, resetPasswordCommand.getNewPassword());
        return HandleResponse.ok();
    }
}
