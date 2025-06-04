package com.shop.clothing.delivery.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CreateShipOrderResponse {
    private String id;
    private int cod;
    private int fee;
    private String trackingNumber;
    private String carrier;
    private String createdAt;


}
