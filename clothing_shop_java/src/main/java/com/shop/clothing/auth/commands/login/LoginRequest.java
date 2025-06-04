package com.shop.clothing.auth.commands.login;


import com.shop.clothing.common.Cqrs.IRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest implements IRequest<Integer> {
    private String username;
    private String password;
}
