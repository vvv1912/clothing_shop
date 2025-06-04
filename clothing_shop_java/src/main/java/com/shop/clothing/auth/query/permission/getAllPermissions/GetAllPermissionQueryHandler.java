package com.shop.clothing.auth.query.permission.getAllPermissions;

import com.shop.clothing.auth.dto.PermissionDto;
import com.shop.clothing.auth.repository.PermissionRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetAllPermissionQueryHandler implements IRequestHandler<GetAllPermissionQuery, List<PermissionDto>> {
    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;
    @Override
    public HandleResponse<List<PermissionDto>> handle(GetAllPermissionQuery getAllPermissionQuery)  {
            var permissions = permissionRepository.findAll();
            var permissionDtos = permissions.stream().map(permission -> {
                return modelMapper.map(permission, PermissionDto.class);
            }).toList();
            return HandleResponse.ok(permissionDtos);


    }
}
