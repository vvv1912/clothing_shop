package com.shop.clothing.rating.query.getRatingOfMyOrderItem;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.rating.dto.RatingDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMyRatingOfMyOrderItemQuery implements IRequest<RatingDto>{
    @NotEmpty(message = "Mã đơn hàng không được để trống")
    private String orderId;
    @Min(value = 1, message = "Mã sản phẩm không được để trống")
    private int productOptionId;
    private String userId;

}
