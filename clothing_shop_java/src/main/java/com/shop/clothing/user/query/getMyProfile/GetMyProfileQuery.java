package com.shop.clothing.user.query.getMyProfile;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.user.UserDto;


public record GetMyProfileQuery() implements IRequest<UserDto> {

}
