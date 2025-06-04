package com.shop.clothing.rating.command.updateRating;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.config.CurrentUserService;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.rating.Rating;
import com.shop.clothing.rating.RatingRepository;
import com.shop.clothing.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class UpdateRatingCommandHandler implements IRequestHandler<UpdateRatingCommand, Void> {
    private final OrderRepository orderRepository;
    private final RatingRepository ratingRepository;
    private final CurrentUserService currentUserService;
    private final ProductOptionRepository productOptionRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(UpdateRatingCommand command)  {
        var userId = currentUserService.getCurrentUserId();
        var rating = ratingRepository.findById(command.getId());
        if (rating.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Đánh giá không tồn tại");
        }
        if (!rating.get().getUser().getUserId().equals(userId.orElse(""))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền sửa đánh giá này");
        }
        rating.get().setContent(command.getContent());
        rating.get().setValue(command.getValue());
        ratingRepository.save(rating.get());

        return HandleResponse.ok();
    }
}
