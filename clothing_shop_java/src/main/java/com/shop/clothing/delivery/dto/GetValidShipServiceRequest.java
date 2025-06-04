package com.shop.clothing.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetValidShipServiceRequest {
    public  String toCity;
    public  String toDistrict;
    public int orderValue;
    @Builder.Default
    public int cod=0;
    @Builder.Default
    public int widthInCm =20;
    @Builder.Default
    public int heightInCm =5;
    @Builder.Default
    public int lengthInCm =20;
    @Builder.Default
    public int weightInGram =300;


}
