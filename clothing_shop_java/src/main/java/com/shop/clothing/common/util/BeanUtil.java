package com.shop.clothing.common.util;

import com.shop.clothing.common.Cqrs.ISender;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
@AllArgsConstructor
public class BeanUtil {
    private final ISender   sender;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    public ClientUtil clientUtil() {
        return new ClientUtil(sender);
    }
    @Bean
    public SlugUtil slugUtil() {
        return new SlugUtil();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    // hh:mm dd/MM/yyyy
    // date formatter
   
}
