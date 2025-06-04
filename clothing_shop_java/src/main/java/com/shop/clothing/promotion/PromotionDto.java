package com.shop.clothing.promotion;

import com.shop.clothing.order.entity.Order;
import com.shop.clothing.payment.entity.enums.PromotionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class PromotionDto {

    private int promotionId;

    private String code;

    private String name;

    private String description;

    private int discount;

    private PromotionType type;

    private int minOrderAmount;

    private int maxValue;

    private Date startDate;

    private Date endDate;

    private boolean active;

    private int stock;

}
