package com.shop.clothing.common;

import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationRequest {
    @Min(value = 1,message = "Page must be greater than 0")

    protected int page = 1;
    protected int pageSize = 10;
    protected String sortField="";
    protected String sortDir="asc";
    @Builder.Default
    protected String keyword = "";

    public Sort.Direction getSortDirection() {
        return sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public Pageable getPageable(String defaultSortField) {
        if (sortField.isEmpty()) {
            sortField = defaultSortField;
        }
        return org.springframework.data.domain.PageRequest.of(page - 1, pageSize, getSortDirection(), sortField);
    }
}
