package com.shop.clothing.order.command.createOrder;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.validation.NullOrNotBlank;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CreateOrderCommand implements IRequest<String> {

    @Getter
    @Setter
    public static class OrderItem {

        private int productOptionId;
        private int quantity;
    }

    @NotEmpty(message = "Cần chọn sản phẩm")
    public List<OrderItem> orderItems;
    @NotEmpty(message = "Cần nhập tên khách hàng")
    private String customerName;
    @NotEmpty(message = "Cần nhập địa chỉ")
    private String address;
    @NotEmpty(message = "Cần nhập số điện thoại")
    @Pattern(regexp = "^(0|\\+84)\\d{9,11}$", message = "Số điện thoại không đúng định dạng")
    private String phoneNumber;
    @Email(message = "Email không đúng định dạng")
    @NotEmpty(message = "Cần nhập email")
    private String email;
    private String note;
    private String promotionCode;
    @NotNull(message = "Phương thức thanh toán không được để trống")
    private PaymentMethod paymentMethod = PaymentMethod.COD;
    @NullOrNotBlank(message = "Cần chọn dịch vụ vận chuyển")

    private String shipServiceId;
}
