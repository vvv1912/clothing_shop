package com.shop.clothing.auth;

import com.shop.clothing.user.entity.User;
import com.shop.clothing.auth.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("userDetailsService")
@Transactional
public class UserDetailServiceImpl implements UserDetailsService, UserDetailsPasswordService {
    private IUserRepository IUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        var useByEmail = IUserRepository.findByEmail(username);
        if (useByEmail.isPresent()) {
            return useByEmail.get();
        }
        var userByPhoneNumber = IUserRepository.findByPhoneNumber(username);
        if (userByPhoneNumber.isPresent()) {
            return userByPhoneNumber.get();
        }
        var userById = IUserRepository.findById(username);
        if (userById.isPresent()) {
            return userById.get();
        }

        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        var userEntity = IUserRepository.findByEmail(user.getUsername());
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        userEntity.get().setPasswordHash(passwordEncoder.encode(newPassword));
        IUserRepository.save(userEntity.get());
        return userEntity.get();
    }
}
