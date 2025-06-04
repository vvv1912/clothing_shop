package com.shop.clothing.report.query.getTopSoldProductReport;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.report.dto.ProductReportDto;
import lombok.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class GetTopSoldProductReportQuery implements IRequest<List<ProductReportDto>> {
   private Long startDateTimestamp;
 private    Long endDateTimeStamp;

    public Date getStartDate() {
        return startDateTimestamp == null ?null: new Date(startDateTimestamp);
    }

    public Date getEndDate() {
        return endDateTimeStamp == null ? null : new Date(endDateTimeStamp);
    }

}
