package com.shop.clothing.auth.commands.forgotPassword;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.Email;

public record ForgotPasswordCommand(@Email(message = "email không hợp lệ") String email) implements IRequest<Void> {
}
