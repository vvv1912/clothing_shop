package com.shop.clothing.user.command.updateAvatar;

import com.shop.clothing.common.Cqrs.IRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class UpdateAvatarCommand implements IRequest<String> {
    private MultipartFile file;
}
