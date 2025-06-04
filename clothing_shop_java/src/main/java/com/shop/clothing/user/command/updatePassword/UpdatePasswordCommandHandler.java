package com.shop.clothing.user.command.updatePassword;


import com.shop.clothing.auth.repository.IUserRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.config.ICurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UpdatePasswordCommandHandler implements IRequestHandler<UpdatePasswordCommand, Void> {
    private final PasswordEncoder passwordEncoder;
    private final ICurrentUserService currentUserService;
    private final IUserRepository IUserRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(UpdatePasswordCommand updatePasswordCommand) throws Exception {
        var userId = currentUserService.getCurrentUserId();
        if (userId.isEmpty()) {
            return HandleResponse.error("Bạn chưa đăng nhập");
        }
        var user = IUserRepository.findById(userId.get());
        if (user.isEmpty()) {
            return HandleResponse.error("Không tìm thấy người dùng",HttpStatus.NOT_FOUND);
        }
        var userEntity = user.get();
        if (!passwordEncoder.matches(updatePasswordCommand.getOldPassword(), userEntity.getPasswordHash())) {
            return HandleResponse.error("Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST);
        }
        userEntity.setPasswordHash(passwordEncoder.encode(updatePasswordCommand.getNewPassword()));
        IUserRepository.save(userEntity);
        return HandleResponse.ok();
    }
}
