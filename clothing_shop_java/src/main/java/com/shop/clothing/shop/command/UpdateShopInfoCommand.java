package com.shop.clothing.shop.command;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Setter
@Getter
public class UpdateShopInfoCommand implements IRequest<Void> {
    @NotEmpty(message = "Tên cửa hàng không được để trống")
    public String shopName;
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Số điện thoại không hợp lệ")
    public String shopPhone;
    @Email(message = "Email không hợp lệ")
    public String shopEmail;
    @NotEmpty(message = "Thành phố không được để trống")
    public String shopCity;
    @NotEmpty(message = "Quận/Huyện không được để trống")
    public String shopWard;
    @NotEmpty(message = "Phường/Xã không được để trống")
    public String shopDistrict;
    @NotEmpty(message = "Đường không được để trống")
    public String shopStreet;
    @NotEmpty(message = "Tên chủ cửa hàng không được để trống")
    public String shopOwner;
//    @URL(message = "Logo không hợp lệ")
    public String shopLogo = "";

}
