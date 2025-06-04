package com.shop.clothing.delivery.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class GetValidShipServiceResponse {
    private String id;
    private  String carrierName;
    private  String carrierLogo;
    private  String service;
    private  String expected;
    private int totalFree;
    private int totalAmount;
}
