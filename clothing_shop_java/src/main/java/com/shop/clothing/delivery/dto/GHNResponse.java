package com.shop.clothing.delivery.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GHNResponse<T>{
    private String code;
    private String message;
    private T data;

}
