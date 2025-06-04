package com.shop.clothing.auth.commands.permission.updatePermissionCommand;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdatePermissionCommand implements IRequest<Void> {
    @NotEmpty(message = "Mã quyền không được để trống")
    private String normalizedName;
    @NotEmpty(message = "Tên quyền không được để trống")
    private String displayName;
    @NotEmpty(message = "Mô tả quyền không được để trống")
    private String description;
}
