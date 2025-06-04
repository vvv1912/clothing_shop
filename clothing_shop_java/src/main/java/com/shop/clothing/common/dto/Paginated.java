package com.shop.clothing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collection;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Paginated<T> {
    private Collection<T> data;
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
    private boolean hasPrevious;

    public static <T> Paginated<T> of(Page<T> page) {
        return new Paginated<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalPages(), page.getTotalElements(), page.hasNext(), page.hasPrevious());
    }

}
