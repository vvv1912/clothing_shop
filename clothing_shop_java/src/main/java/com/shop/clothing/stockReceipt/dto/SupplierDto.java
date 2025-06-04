package com.shop.clothing.stockReceipt.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupplierDto {
    private int supplierId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String description;
}
