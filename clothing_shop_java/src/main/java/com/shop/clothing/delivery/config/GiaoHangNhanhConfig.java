package com.shop.clothing.delivery.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiaoHangNhanhConfig {
    @Value("${giaohangnhanh.api-key}")
    private  String apiKey;
    @Value("${giaohangnhanh.shop-id}")
    private  String shopId;
}
