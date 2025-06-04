package com.shop.clothing.promotion.command.updatePromotion;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.payment.entity.enums.PromotionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter
@Setter

public class UpdatePromotionCommand implements IRequest<Void> {
    @Min(value = 1, message = "Mã khuyến mãi không hợp lệ")
    private int promotionId;
    @NotEmpty(message = "Mã khuyến mãi không được để trống")
    @Length(max = 20, min = 4, message = "Mã khuyến mãi không được quá 20 ký tự và ít nhất 4 ký tự")
    private String code;
    @NotEmpty(message = "Tên khuyến mãi không được để trống")
    private String name;
    private String description;
    @Min(value = 0, message = "Giá trị giảm giá không hợp lệ")
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
