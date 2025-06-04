package com.shop.clothing.user.command.toggleRole;

import com.shop.clothing.auth.entity.Role;
import com.shop.clothing.auth.repository.IUserRepository;
import com.shop.clothing.auth.repository.RoleRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class ToggleRoleToAccountCommandHandler implements IRequestHandler<ToggleRoleToAccountCommand, Void> {
    private final IUserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(ToggleRoleToAccountCommand toggleRoleToAccountCommand) throws Exception {
        var user = userRepository.findById(toggleRoleToAccountCommand.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        var role = roleRepository.findById(toggleRoleToAccountCommand.getRoleId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        // check if user has role
        boolean isInRole = false;
        for (Role r : user.getRoles()) {
            if (r.getNormalizedName().equals(role.getNormalizedName())) {
                isInRole = true;
                break;
            }
        }
        if (isInRole) {
            user.getRoles().remove(role);
        } else {
            user.getRoles().add(role);
        }
        if (user.getRoles().size() <= 1) {
            user.setCustomer(user.getRoles().isEmpty() || user.getRoles().get(0).getNormalizedName().equals("ROLE_CUSTOMER"));
        }else {
            user.setCustomer(false);
        }
        userRepository.save(user);
        return HandleResponse.ok();
    }
}
