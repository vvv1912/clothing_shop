package com.shop.clothing.auth.commands.role.addPermissionToRole;

import com.shop.clothing.auth.repository.PermissionRepository;
import com.shop.clothing.auth.repository.RoleRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AddPermissionToRoleCommandHandler implements IRequestHandler<AddPermissionToRoleCommand, Void> {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    @Override
    @Transactional
    public HandleResponse<Void> handle(AddPermissionToRoleCommand addPermissionToRoleCommand) {
        var role = roleRepository.findById(addPermissionToRoleCommand.getRoleName());
        if (role.isEmpty()) {
            return HandleResponse.error("Role with name " + addPermissionToRoleCommand.getRoleName() + " not found");
        }
        var permission = permissionRepository.findByName(addPermissionToRoleCommand.getPermissionName());
        if (permission.isEmpty()) {
            return HandleResponse.error("Permission with name " + addPermissionToRoleCommand.getPermissionName() + " not found");
        }
        var role1 = role.get();
        var listPermission = role1.getPermissions();
        if (listPermission == null) {
            listPermission = new ArrayList<>();
        }
        listPermission.add(permission.get());
        role1.setPermissions(listPermission);
        roleRepository.save(role1);
        return HandleResponse.ok(null);
    }
}
