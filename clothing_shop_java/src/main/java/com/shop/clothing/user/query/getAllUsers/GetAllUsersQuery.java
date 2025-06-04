package com.shop.clothing.user.query.getAllUsers;


import com.shop.clothing.common.PaginationRequest;
import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.user.UserBriefDto;

import com.shop.clothing.user.UserDto;
import lombok.*;


@Getter
@Setter

public class GetAllUsersQuery extends PaginationRequest implements IRequest<Paginated<UserDto>>{
    private String accountType = "ALL";

}
