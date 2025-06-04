package com.shop.clothing.report.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;



@Getter
@Setter
public class SoldReportDto {
    private Date date;
    private int totalOrder;
    private int totalRevenue;
    private int totalQuantitySold;

}
