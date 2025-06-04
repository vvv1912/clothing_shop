package com.shop.clothing.config;

import com.shop.clothing.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrentUserService implements ICurrentUserService {
    private final UserDetailsService userDetailsService;

    @Override
    public Optional<String> getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        return Optional.ofNullable(((User) authentication.getPrincipal()).getUserId());
    }


    public Optional<UserDetails> getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        return Optional.ofNullable((UserDetails) authentication.getPrincipal());
    }
}
