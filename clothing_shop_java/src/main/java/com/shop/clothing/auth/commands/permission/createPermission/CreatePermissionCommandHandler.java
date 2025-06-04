package com.shop.clothing.auth.commands.permission.createPermission;

import com.shop.clothing.auth.commands.role.addPermissionToRole.AddPermissionToRoleCommand;
import com.shop.clothing.auth.entity.Permission;
import com.shop.clothing.auth.repository.PermissionRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.Cqrs.ISender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor

public class CreatePermissionCommandHandler implements IRequestHandler<CreatePermissionCommand, String> {
    private final PermissionRepository permissionRepository;
    private final ISender sender;

    @Override
    @Transactional
    public HandleResponse<String> handle(CreatePermissionCommand createPermissionCommand) {
        var existPermission = permissionRepository.findById(createPermissionCommand.getNormalizedName());
        if (existPermission.isPresent()) {
            return HandleResponse.error("Quyền với mã " + createPermissionCommand.getNormalizedName() + " đã tồn tại");
        }
        var permission = Permission.builder()
                .normalizedName(createPermissionCommand.getNormalizedName())
                .displayName(createPermissionCommand.getDisplayName())
                .description(createPermissionCommand.getDescription())
                .build();
        permissionRepository.save(permission);
        sender.send(AddPermissionToRoleCommand.builder()
                .permissionName(permission.getNormalizedName())
                .roleName("ROLE_ADMIN")
                .build());
        return HandleResponse.ok(permission.getNormalizedName());
    }
}
