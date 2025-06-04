package com.shop.clothing.scheduleTask;

import com.shop.clothing.order.entity.enums.OrderStatus;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.product.repository.ProductRepository;
import com.shop.clothing.rating.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Component
public class ProductTask {
    private final ProductRepository productRepository;
    private final RatingRepository ratingRepository;

    // every day at 3:00 AM
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void updateProductTotalSold() {
        System.out.println("Cập nhật số lượng sản phẩm đã bán");
        productRepository.updateTotalSold(new OrderStatus[]{OrderStatus.DELIVERED});
        productRepository.updateTotalToZeroWhereSoldIsNull();

    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateProductAverageRating() {
        System.out.println("Cập nhật đánh giá trung bình của sản phẩm");
        var allProduct = productRepository.findAll();
        allProduct.forEach(product -> {
            float numOfRate = 0;
            float totalRate = 0;
            var ratings = ratingRepository.findAllByProductOptionProductProductId(product.getProductId());
            for (var rating : ratings) {
                numOfRate++;
                totalRate += rating.getValue();
            }
            if (numOfRate > 0) {
                product.setAverageRating(totalRate / numOfRate);
            } else {
                product.setAverageRating(0);
            }
            productRepository.save(product);
        });
    }


}
