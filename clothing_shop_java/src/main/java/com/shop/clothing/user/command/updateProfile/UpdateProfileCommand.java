package com.shop.clothing.user.command.updateProfile;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@Getter
@Setter
public class UpdateProfileCommand implements IRequest<Void> {
    @NotBlank(message = "Họ không được để trống")
    private String firstName;
    @NotBlank(message = "Tên không được để trống")
    private String lastName;
    @Email(message = "Email không hợp lệ")
    private String email;
    @Size(min = 10, max = 100, message = "Địa chỉ không hợp lệ")
    private String address;
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

}
