package com.shop.clothing.rating.query.getAllRating;


import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.PaginationRequest;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.rating.dto.RatingDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAllRatingsQuery extends PaginationRequest implements IRequest<Paginated<RatingDto>> {

//    private Integer productId;
//    private

}
