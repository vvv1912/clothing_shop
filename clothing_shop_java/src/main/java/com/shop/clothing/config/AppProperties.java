package com.shop.clothing.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppProperties {
    @Value("${app.host}")
    private String host ;
    @Value("${spring.mail.username}")
    private String ownerEmail;

}
