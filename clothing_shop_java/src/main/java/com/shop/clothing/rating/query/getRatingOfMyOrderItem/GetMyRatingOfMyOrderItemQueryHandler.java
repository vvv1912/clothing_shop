package com.shop.clothing.rating.query.getRatingOfMyOrderItem;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.rating.RatingRepository;
import com.shop.clothing.rating.dto.RatingDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class GetMyRatingOfMyOrderItemQueryHandler implements IRequestHandler<GetMyRatingOfMyOrderItemQuery, RatingDto> {
    private final RatingRepository ratingRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional(readOnly = true)
    public HandleResponse<RatingDto> handle(GetMyRatingOfMyOrderItemQuery getMyRatingOfMyOrderItemQuery) throws Exception {
        var rating = ratingRepository.findFirstByUserIdAndProductOptionIdAndOrderId(getMyRatingOfMyOrderItemQuery.getUserId(), getMyRatingOfMyOrderItemQuery.getProductOptionId(), getMyRatingOfMyOrderItemQuery.getOrderId());
        rating.orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Không tìm thấy đánh giá"));
        return HandleResponse.ok(modelMapper.map(rating.get(), RatingDto.class));
    }
}
