package com.shop.clothing.auth.commands.role.createRole;

import com.shop.clothing.auth.entity.Role;
import com.shop.clothing.auth.repository.RoleRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreateRoleCommandHandler implements IRequestHandler<CreateRoleCommand, String> {

    private final RoleRepository roleRepository;
    @Override
    @Transactional
    public HandleResponse<String> handle(CreateRoleCommand createRoleCommand) {
        var existRole = roleRepository.findById(createRoleCommand.getNormalizedName());
        if (existRole.isPresent()) {
            return HandleResponse.error("Role with name " + createRoleCommand.getNormalizedName() + " already exist");
        }
        var role = Role.builder()
                .normalizedName(createRoleCommand.getNormalizedName())
                .displayName(createRoleCommand.getDisplayName())
                .description(createRoleCommand.getDescription())
                .build();
        roleRepository.save(role);
        return HandleResponse.ok(role.getNormalizedName());
    }
}
