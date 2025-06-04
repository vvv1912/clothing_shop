package com.shop.clothing.user.command.toggleLockAccount;

import com.shop.clothing.common.Cqrs.IRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToggleLockAccountCommand implements IRequest<Void> {
    private String userId;

}
