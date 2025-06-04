package com.shop.clothing.promotion.query.getAllPromotions;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.PaginationRequest;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.payment.entity.enums.PromotionType;
import com.shop.clothing.promotion.PromotionDto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class GetAllPromotionQuery extends PaginationRequest implements IRequest<Paginated<PromotionDto>> {
    private Long fromDateTimestamp;
    private Long toDateTimestamp;
    public LocalDateTime getFromDate() {
      return fromDateTimestamp != null ? Instant.ofEpochMilli(fromDateTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate().atStartOfDay() : null;
    }

    public LocalDateTime getToDate() {
        return toDateTimestamp != null ? Instant.ofEpochMilli(toDateTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate().atStartOfDay() : null;
    }


}
