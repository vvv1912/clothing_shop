package com.shop.clothing.delivery.goShip;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class GoShipProperties {
    @Value("${go-ship.client-id}")
    private String clientId;
    @Value("${go-ship.client-secret}")
    private String clientSecret;
    @Value("${go-ship.access-token}")
    private String accessToken;
    @Value("${go-ship.endpoint}")
    private String endpoint;


}
