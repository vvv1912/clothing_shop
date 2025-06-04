package com.shop.clothing.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface ICurrentUserService {
    public Optional<String> getCurrentUserId();
    public Optional<UserDetails> getCurrentUser();


}
