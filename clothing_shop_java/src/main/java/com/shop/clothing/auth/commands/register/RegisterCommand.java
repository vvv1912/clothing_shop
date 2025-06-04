package com.shop.clothing.auth.commands.register;

import com.shop.clothing.user.entity.User;
import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder

public class RegisterCommand  implements IRequest<User> {
    @NotEmpty(message = "Họ và tên đệm không được để trống")
    private String lastName;
    @NotEmpty(message = "Tên không được để trống")
    private String firstName;
    @NotEmpty(message = "Email không được để trống")
    @Email
    private String email;
    @NotEmpty(message = "Mật khẩu không được để trống")
    @Length(min = 8,message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String rawPassword;
}
