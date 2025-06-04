package com.shop.clothing.user;

import com.shop.clothing.auth.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class UserDto extends UserBriefDto {


    private String email;

    private String phoneNumber;

    private String address;


    private boolean isEmailVerified = false;

    private boolean isAccountEnabled = true;
    private boolean isCustomer = true;
    private Date createdAt = new Date();
    private Collection<String> permissions;
    private Collection<RoleDto> roles;

    public String toJsonObject() {
        return "{" +
                "\"userId\":\"" + userId + '\"' +
                ", \"firstName\":\"" + firstName + '\"' +
                ", \"lastName\":\"" + lastName + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"phoneNumber\":\"" + phoneNumber + '\"' +
                ", \"address\":\"" + address + '\"' +
                ", \"avatarUrl\":\"" + avatarUrl + '\"' +
                ", \"isEmailVerified\":" + isEmailVerified +
                ", \"isAccountEnabled\":" + isAccountEnabled +
                ", \"isCustomer\":" + isCustomer +
                '}';
    }
}
