package com.shop.clothing.stockReceipt.query.getAllSuppliers;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.stockReceipt.dto.StockReceiptBriefDto;
import com.shop.clothing.stockReceipt.dto.SupplierDto;
import com.shop.clothing.stockReceipt.entity.StockReceipt;
import com.shop.clothing.stockReceipt.entity.Supplier;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetAllSuppliersQueryHandler implements IRequestHandler<GetAllSuppliersQuery, Paginated<SupplierDto>> {
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Override
    public HandleResponse<Paginated<SupplierDto>> handle(GetAllSuppliersQuery query) throws Exception {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Supplier.class);
        var root = criteriaQuery.from(Supplier.class);
        var predicates = criteriaBuilder.conjunction();
        if (query.getKeyword() != null && !query.getKeyword().isEmpty()) {
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("name"), "%" + query.getKeyword() + "%"));
        }
        if (query.getSortField() == null || query.getSortField().isEmpty()) {
            query.setSortField("name");
        }
        if (query.getSortDir() == null || query.getSortDir().isEmpty()) {
            query.setSortDir("desc");
        }
        criteriaQuery.where(predicates);

        var sort = query.getSortDirection();
        criteriaQuery.orderBy(sort.isAscending() ? criteriaBuilder.asc(root.get(query.getSortField())) : criteriaBuilder.desc(root.get(query.getSortField())));
        var q = entityManager.createQuery(criteriaQuery);
        var totalElements = q.getResultList().size();
        q.setFirstResult((query.getPage() - 1) * query.getPageSize());
        q.setMaxResults(query.getPageSize());
        var suppliers = q.getResultList();
        var supplierDtos = suppliers.stream().map(supplier -> modelMapper.map(supplier, SupplierDto.class)).toList();
        var paginated = Paginated.<SupplierDto>builder()
                .page(query.getPage())
                .hasNext(totalElements > query.getPage() * query.getPageSize())
                .hasPrevious(query.getPage() > 1)
                .totalElements(totalElements)
                .pageSize(query.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / query.getPageSize()))
                .data(supplierDtos).totalElements(totalElements).build();
        return HandleResponse.ok(paginated);

    }
}
