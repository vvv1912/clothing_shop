package com.shop.clothing.promotion.command.createPromotion;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.payment.entity.enums.PromotionType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter
@Setter

public class CreatePromotionCommand implements IRequest<Integer> {
    @NotEmpty(message = "Mã khuyến mãi không được để trống")
    @Length(max = 20, min = 4, message = "Mã khuyến mãi không được quá 20 ký tự và ít nhất 4 ký tự")
    private String code;
    @NotEmpty(message = "Tên khuyến mãi không được để trống")
    private String name;
    private String description;
    @Min(value =1, message = "Giá trị giảm giá không được để trống")
    private int discount;
    @NotNull(message = "Loại khuyến mãi không được để trống")
    private PromotionType type;
    @Min(value = 0, message = "Giá trị đơn hàng tối thiểu không hợp lệ")
    private int minOrderAmount;
    private Integer maxValue;
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private Date startDate;
    @NotNull(message = "Ngày kết thúc không được để trống")
    private Date endDate;
    private boolean active = true;
    private int stock = 0;

}
