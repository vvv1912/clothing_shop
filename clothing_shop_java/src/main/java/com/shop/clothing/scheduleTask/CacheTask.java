package com.shop.clothing.scheduleTask;

import com.shop.clothing.order.repository.OrderItemRepository;
import com.shop.clothing.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CacheTask {


    @CacheEvict(value = "searchProduct", allEntries = true)
    @Scheduled(fixedRate = 1800000)// =30 minutes
    public void clearSearchCache() {

    }

    @CacheEvict(value = "categoryReport", allEntries = true)

    @Scheduled(fixedRate = 1800000)// =30 minutes
    public void clearReport() {

    }

    @CacheEvict(value = "topSoldProductReport", allEntries = true)
    @Scheduled(fixedRate = 1800000)// =30 minutes
    public void clearTopSoldProductReport() {

    }

    @CacheEvict(value = "importReport", allEntries = true)
    @Scheduled(fixedRate = 1800000)// =30 minutes
    public void clearImportReport() {

    }

    @CacheEvict(value = "soldReport", allEntries = true)
    @Scheduled(fixedRate = 1800000)// =30 minutes
    public void clearSoldReport() {

    }

    @CacheEvict(value = "homePage", allEntries = true)
    @Scheduled(fixedRate = 1800000)// =30 minutes
    public void clearHomePageCache() {
    }
}
