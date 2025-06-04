package com.shop.clothing.shop.command;

import com.shop.clothing.common.Cqrs.IRequest;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.bridge.ICommand;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class AddBannerCommand implements IRequest<Void> {
    @URL(message = "Ảnh không hợp lệ")
    public String image;
    @URL(message = "Đường dẫn không hợp lệ")
    public String href;
}
