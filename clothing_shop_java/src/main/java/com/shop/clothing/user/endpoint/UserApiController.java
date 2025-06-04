package com.shop.clothing.user.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.user.UserBriefDto;
import com.shop.clothing.user.UserDto;
import com.shop.clothing.user.command.toggleLockAccount.ToggleLockAccountCommand;
import com.shop.clothing.user.command.toggleRole.ToggleRoleToAccountCommand;
import com.shop.clothing.user.command.updateAvatar.UpdateAvatarCommand;
import com.shop.clothing.user.command.updatePassword.UpdatePasswordCommand;
import com.shop.clothing.user.command.updateProfile.UpdateProfileCommand;
import com.shop.clothing.user.query.getAllUsers.GetAllUsersQuery;
import com.shop.clothing.user.query.getMyProfile.GetMyProfileQuery;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserApiController {
    private final ISender sender;

    @GetMapping()
    public ResponseEntity<Paginated<UserDto>> getUsers(@Valid @ParameterObject GetAllUsersQuery paginationRequest) {
        return ResponseEntity.ok(sender.send(paginationRequest).orThrow());
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserDto> getMyProfile() {
        var query = new GetMyProfileQuery();
        var result = sender.send(query);
        return ResponseEntity.ok(result.orThrow());
    }

    @PostMapping("/my-profile")
    public ResponseEntity<Void> updateMyProfile(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateProfileCommand command) throws Exception {
        sender.send(command).orThrow();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-avatar")
    public ResponseEntity<String> updateMyAvatar(@RequestParam("file") MultipartFile file) throws Exception {
        var command = new UpdateAvatarCommand();
        command.setFile(file);
        return ResponseEntity.ok(sender.send(command).orThrow());
    }

    @PostMapping("/update-password")
    public ResponseEntity<Void> updateMyPassword(@Valid @RequestBody UpdatePasswordCommand command) throws Exception {
        return ResponseEntity.ok(sender.send(command).orThrow());
    }

    @PutMapping("{userId}/toggle-lock-account")
    @Secured("USER_MANAGEMENT")
    public ResponseEntity<Void> toggleLockAccount(@PathVariable String userId) {
        var command = new ToggleLockAccountCommand();
        command.setUserId(userId);
        return ResponseEntity.ok(sender.send(command).orThrow());
    }

    @PutMapping("toggle-role")
    @Secured("USER_MANAGEMENT")
    public ResponseEntity<Void> toggleRole(@Valid @RequestBody ToggleRoleToAccountCommand command) throws Exception {
        return ResponseEntity.ok(sender.send(command).orThrow());
    }
}
