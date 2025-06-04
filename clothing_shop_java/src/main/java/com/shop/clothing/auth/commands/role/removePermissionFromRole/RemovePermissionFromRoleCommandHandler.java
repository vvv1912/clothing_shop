package com.shop.clothing.auth.commands.role.removePermissionFromRole;

import com.shop.clothing.auth.repository.PermissionRepository;
import com.shop.clothing.auth.repository.RoleRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RemovePermissionFromRoleCommandHandler implements IRequestHandler<RemovePermissionFromRoleCommand, Void> {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    @Override
    @Transactional
    public HandleResponse<Void> handle(RemovePermissionFromRoleCommand command) {
        var role = roleRepository.findById(command.getRoleName());
        if (role.isEmpty()) {
            return HandleResponse.error("Role with name " + command.getRoleName() + " not found");
        }
        var permission = permissionRepository.findByName(command.getPermissionName());
        if (permission.isEmpty()) {
            return HandleResponse.error("Permission with name " + command.getPermissionName() + " not found");
        }
        var role1 = role.get();
        var listPermission = role1.getPermissions();
        if (listPermission == null) {
            return HandleResponse.ok();
        }
        listPermission.removeIf(permission1 -> permission1.getNormalizedName().equals(command.getPermissionName()));
        role1.setPermissions(listPermission);
        roleRepository.save(role1);
        return HandleResponse.ok(null);
    }
}
