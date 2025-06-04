package com.shop.clothing.report.query.getSoldReport;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.order.entity.enums.OrderStatus;
import com.shop.clothing.report.dto.SoldReportDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class GetSoldReportQuery implements IRequest<List<SoldReportDto>> {
    private Long startDateTime;
    private Long endDateTime;


    public Date getStartDate() {
        return startDateTime == null ? null : new Date(startDateTime);
    }

    public Date getEndDate() {
        return endDateTime == null ? null : new Date(endDateTime);
    }


}
