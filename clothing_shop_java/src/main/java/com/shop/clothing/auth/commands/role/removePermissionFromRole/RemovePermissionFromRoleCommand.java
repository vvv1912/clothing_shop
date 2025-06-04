package com.shop.clothing.auth.commands.role.removePermissionFromRole;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemovePermissionFromRoleCommand implements IRequest<Void> {
    @NotEmpty(message = "Tên vai trò không được để trống")
    private String roleName;
    @NotEmpty(message = "Tên quyền không được để trống")
    private String permissionName;
}
