package com.shop.clothing.auth.commands.role.updateRole;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRoleCommand implements IRequest<Void> {
    @NotEmpty(message = "Mã vai trò không được để trống")
    private String normalizedName;
    @NotEmpty(message = "Tên vai trò không được để trống")
    private String displayName;
    @NotEmpty(message = "Mô tả vai trò không được để trống")
    private String description;
}
