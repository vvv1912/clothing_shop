package com.shop.clothing.auth.commands.role.deleteRole;

import com.shop.clothing.common.Cqrs.IRequest;

public record DeleteRoleCommand(String roleName) implements IRequest<Void> {
}
