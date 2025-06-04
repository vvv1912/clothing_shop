package com.shop.clothing.stockReceipt.query.getAllStockReceipts;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.stockReceipt.dto.StockReceiptBriefDto;
import com.shop.clothing.stockReceipt.entity.StockReceipt;
import com.shop.clothing.stockReceipt.repository.StockReceiptRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetAllStockReceiptsQueryHandler implements IRequestHandler<GetAllStockReceiptsQuery, Paginated<StockReceiptBriefDto>> {
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Override
    public HandleResponse<Paginated<StockReceiptBriefDto>> handle(GetAllStockReceiptsQuery getAllStockReceiptsQuery) throws Exception {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(StockReceipt.class);
        var root = criteriaQuery.from(StockReceipt.class);
        var predicates = criteriaBuilder.conjunction();
        if (getAllStockReceiptsQuery.getStartDateObject() != null) {
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), getAllStockReceiptsQuery.getStartDateObject()));
        }
        if (getAllStockReceiptsQuery.getEndDateObject() != null) {
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), getAllStockReceiptsQuery.getEndDateObject()));
        }
        if (getAllStockReceiptsQuery.getSupplierId() != null && getAllStockReceiptsQuery.getSupplierId() != 0) {
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("supplier").get("supplierId"), getAllStockReceiptsQuery.getSupplierId()));
        }
        if (getAllStockReceiptsQuery.getTotalFrom() != 0) {
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.greaterThanOrEqualTo(root.get("total"), getAllStockReceiptsQuery.getTotalFrom()));
        }
        if (getAllStockReceiptsQuery.getTotalTo() != Integer.MAX_VALUE) {
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.lessThanOrEqualTo(root.get("total"), getAllStockReceiptsQuery.getTotalTo()));
        }
        criteriaQuery.where(predicates);
        if (getAllStockReceiptsQuery.getSortField() == null || getAllStockReceiptsQuery.getSortField().isEmpty()) {
            getAllStockReceiptsQuery.setSortField("createdDate");
        }
        if (getAllStockReceiptsQuery.getSortDir() == null || getAllStockReceiptsQuery.getSortDir().isEmpty()) {
            getAllStockReceiptsQuery.setSortDir("desc");
        }
        var sort = getAllStockReceiptsQuery.getSortDirection();
        criteriaQuery.orderBy(sort.isAscending() ? criteriaBuilder.asc(root.get(getAllStockReceiptsQuery.getSortField())) : criteriaBuilder.desc(root.get(getAllStockReceiptsQuery.getSortField())));


        var query = entityManager.createQuery(criteriaQuery);
        var totalElements = query.getResultList().size();
        query.setFirstResult((getAllStockReceiptsQuery.getPage() - 1) * getAllStockReceiptsQuery.getPageSize());
        query.setMaxResults(getAllStockReceiptsQuery.getPageSize());
        var stockReceipts = query.getResultList();
        var stockReceiptBriefDtos = stockReceipts.stream().map(stockReceipt -> {
            return modelMapper.map(stockReceipt, StockReceiptBriefDto.class);
        }).toList();
        var paginated = Paginated.<StockReceiptBriefDto>builder()
                .page(getAllStockReceiptsQuery.getPage())
                .hasNext(totalElements > getAllStockReceiptsQuery.getPage() * getAllStockReceiptsQuery.getPageSize())
                .hasPrevious(getAllStockReceiptsQuery.getPage() > 1)
                .totalElements(totalElements)
                .pageSize(getAllStockReceiptsQuery.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / getAllStockReceiptsQuery.getPageSize()))
                .data(stockReceiptBriefDtos).totalElements(totalElements).build();
        return HandleResponse.ok(paginated);
    }
}
