package com.shop.clothing.auth.endpoint;

import com.shop.clothing.auth.dto.PermissionDto;
import com.shop.clothing.common.Cqrs.ISender;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
@Secured("ROLE_MANAGEMENT")
@AllArgsConstructor
public class PermissionApiController {
        private final ISender sender;
    @GetMapping()
    public ResponseEntity<List<PermissionDto>> getAllPermissions() {
        var query = new com.shop.clothing.auth.query.permission.getAllPermissions.GetAllPermissionQuery();
        var result = sender.send(query);
        return ResponseEntity.ok(result.orThrow());
    }
}
