package com.shop.clothing.user.command.updatePassword;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordCommand implements IRequest<Void> {
    @NotEmpty(message = "Mật khẩu cũ không được để trống")
    private String oldPassword;
    @Size(min = 4, message = "Mật khẩu phải có ít nhất 4 ký tự")

    private String newPassword;
}
