package com.shop.clothing.user.command.updateAvatar;

import com.shop.clothing.auth.repository.IUserRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.config.ICurrentUserService;
import com.shop.clothing.file.command.uploadFile.UploadFileCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UpdateAvatarCommandHandler implements IRequestHandler<UpdateAvatarCommand,String> {
    private final ISender sender;
    private final IUserRepository IUserRepository;
    private final ICurrentUserService currentUserService;
    @Override
    @Transactional
    public HandleResponse<String> handle(UpdateAvatarCommand updateAvatarCommand) throws Exception {
        var currentUserId = currentUserService.getCurrentUserId();
        if (currentUserId.isEmpty()) {
            return HandleResponse.error("Bạn chưa đăng nhập");
        }
        var user = IUserRepository.findById(currentUserId.get());
        if (user.isEmpty()) {
            return HandleResponse.error("Không tìm thấy người dùng");
        }
        var uploadResult = sender.send(new UploadFileCommand(updateAvatarCommand.getFile()));
        if (uploadResult.hasError()) {
            return uploadResult;
        }
        user.get().setAvatarUrl(uploadResult.get());
        IUserRepository.save(user.get());
       return uploadResult;
    }
}
