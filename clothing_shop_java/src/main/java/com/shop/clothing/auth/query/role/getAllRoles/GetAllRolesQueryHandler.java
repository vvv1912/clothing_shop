package com.shop.clothing.auth.query.role.getAllRoles;

import com.shop.clothing.auth.dto.RoleDto;
import com.shop.clothing.auth.repository.RoleRepository;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class GetAllRolesQueryHandler implements IRequestHandler<GetAllRolesQuery, List<RoleDto>>{
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public HandleResponse<List<RoleDto>> handle(GetAllRolesQuery getAllRolesQuery){
        var roles = roleRepository.findAll();
        var roleDtos = roles.stream().map(role -> {
            return modelMapper.map(role, RoleDto.class);
        }).toList();
        return HandleResponse.ok(roleDtos);
    }
}
