package com.shop.clothing.rating.query.getAllRatingOfProductId;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.product.entity.ProductOption;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.product.repository.ProductRepository;
import com.shop.clothing.rating.RatingRepository;
import com.shop.clothing.rating.dto.RatingDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class GetAllRatingOfProductIdQueryHandler implements IRequestHandler<GetAllRatingOfProductIdQuery, Paginated<RatingDto>> {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public HandleResponse<Paginated<RatingDto>> handle(GetAllRatingOfProductIdQuery getAllRatingOfProductIdQuery) throws Exception {
        var product = productRepository.findById(getAllRatingOfProductIdQuery.getProductId());
        if (product.isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại");
        }
        var ratings = ratingRepository.findAllByProductId(getAllRatingOfProductIdQuery.getProductId(), getAllRatingOfProductIdQuery.getPageable("createdDate"));
        var ratingDtos = ratings.map(rating -> modelMapper.map(rating, RatingDto.class));
        return HandleResponse.ok(Paginated.of(ratingDtos));
    }
}
