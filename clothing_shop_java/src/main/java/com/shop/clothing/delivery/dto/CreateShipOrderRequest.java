package com.shop.clothing.delivery.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CreateShipOrderRequest {
    private String rateServiceId;
    //    private String fromName;
//    private String fromPhone;
//    private String fromAddress;
    private String toName;
    private String orderID;
    private String toPhone;
    private String toAddress;
    @Builder.Default
    private int cod = 0;
    private int orderAmount;
    @Builder.Default
    public int widthInCm = 20;
    @Builder.Default
    public int heightInCm = 5;
    @Builder.Default
    public int lengthInCm = 20;
    @Builder.Default
    public int weightInGram = 300;

    private String getProvince(String rawAddress) {
        String[] split = rawAddress.split(",");
        return split[split.length - 1].trim();
    }

    private String getDistrict(String rawAddress) {
        String[] split = rawAddress.split(",");
        return split[split.length - 2].trim();
    }

    private String getWard(String rawAddress) {
        String[] split = rawAddress.split(",");
        return split[split.length - 3].trim();
    }

    private String getDetailAddress(String rawAddress) {
        String[] split = rawAddress.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length - 3; i++) {
            stringBuilder.append(split[i]);
        }
        return stringBuilder.toString().trim();
    }

    public String getToProvince() {
        return getProvince(toAddress);
    }

    public String getToDistrict() {
        return getDistrict(toAddress);
    }

    public String getToWard() {
        return getWard(toAddress);
    }

    public String getToDetailAddress() {
        return getDetailAddress(toAddress);
    }



}
