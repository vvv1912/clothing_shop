package com.shop.clothing.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetProvinceResponse {
    private int provinceID;
    private List<String> nameExtension;
}
