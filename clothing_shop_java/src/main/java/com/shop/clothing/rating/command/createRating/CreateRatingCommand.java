package com.shop.clothing.rating.command.createRating;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRatingCommand implements IRequest<Integer>
{
    private String content;
    @Min(value = 1, message = "Số sao không được để trống")
    private int value;
    private int productOptionId;
    private String orderId;
}
