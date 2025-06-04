package com.shop.clothing.order.query.getAllOrders;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.order.dto.OrderBriefDto;
import com.shop.clothing.order.entity.Order;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.payment.dto.PaymentDto;
import com.shop.clothing.payment.entity.Payment;
import com.shop.clothing.payment.repository.PaymentRepository;
import com.shop.clothing.product.dto.ProductBriefDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
public class GetAllOrderQueryHandler implements IRequestHandler<GetAllOrderQuery, Paginated<OrderBriefDto>> {
    private final EntityManager _entityManager;
    private final ModelMapper _mapper;

    @Override
    @Transactional(readOnly = true)
    public HandleResponse<Paginated<OrderBriefDto>> handle(GetAllOrderQuery getAllOrderQuery) throws Exception {
        var cb = _entityManager.getCriteriaBuilder();
        var cq = cb.createTupleQuery();
        var root = cq.from(Order.class);
        var predicates = cb.conjunction();

        Join<Order, Payment> paymentJoin = root.join("payments");
        Subquery<Timestamp> subquery = cq.subquery(Timestamp.class);
        Root<Payment> subRoot = subquery.from(Payment.class);
        cq.multiselect(root, paymentJoin);
        //lastModifiedDate
        subquery.select(
                cb.greatest(subRoot.get("createdDate").as(Timestamp.class))
        ).where(cb.equal(subRoot.get("order").get("orderId"), root.get("orderId")));

        predicates = cb.and(predicates, cb.equal(paymentJoin.get("createdDate").as(Timestamp.class), subquery));
        if (getAllOrderQuery.getOrderStatus() != null) {
            predicates = cb.and(predicates, cb.equal(root.get("status"), getAllOrderQuery.getOrderStatus()));
        }
        predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("totalAmount"), getAllOrderQuery.getAmountFrom()));
        predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("totalAmount"), getAllOrderQuery.getAmountTo()));
        if (getAllOrderQuery.getPaymentStatus() != null) {
            predicates = cb.and(predicates, cb.equal(paymentJoin.get("status"), getAllOrderQuery.getPaymentStatus()));
        }
        if (getAllOrderQuery.getStartDateObj() != null) {
            predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("createdDate"), getAllOrderQuery.getStartDateObj()));
        }
        if (getAllOrderQuery.getEndDateObj() != null) {
            predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("createdDate"), getAllOrderQuery.getEndDateObj()));
        }
        if (getAllOrderQuery.getKeyword() != null) {
            predicates = cb.and(predicates, cb.or(
                    cb.like(root.get("orderId"), "%" + getAllOrderQuery.getKeyword() + "%"),
                    cb.like(root.get("phoneNumber"), "%" + getAllOrderQuery.getKeyword() + "%"),
                    cb.like(root.get("email"), "%" + getAllOrderQuery.getKeyword() + "%"),
                    cb.like(root.get("customerName"), "%" + getAllOrderQuery.getKeyword() + "%")
            ));
        }
        String sortField = getAllOrderQuery.getSortField();

        if (sortField.isBlank()) {
            sortField = "createdDate";
        }
        if (getAllOrderQuery.getSortDir().equals("desc")) {
            cq.orderBy(cb.desc(root.get(sortField)));
        } else {
            cq.orderBy(cb.asc(root.get(sortField)));
        }
        cq.where(predicates);

        var query = _entityManager.createQuery(cq);

        var count = query.getResultList().size();
        query.setFirstResult((getAllOrderQuery.getPage() - 1) * getAllOrderQuery.getPageSize());
        query.setMaxResults(getAllOrderQuery.getPageSize());
        var orders = query.getResultList();
        Collection<OrderBriefDto> orderBriefDtos = orders.stream().map(tuple -> {
            var order = tuple.get(0, Order.class);
            var payment = tuple.get(1, Payment.class);
            var orderBriefDto = _mapper.map(order, OrderBriefDto.class);
            orderBriefDto.setLatestPayment(_mapper.map(payment, PaymentDto.class));
            return orderBriefDto;
        }).toList();
        var paginated = Paginated.<OrderBriefDto>builder().page(getAllOrderQuery.getPage())
                .pageSize(getAllOrderQuery.getPageSize())
                .data(orderBriefDtos)
                .totalElements(count)
                .totalPages((int) Math.ceil((double) count / getAllOrderQuery.getPageSize()))
                .build();
        paginated.setHasNext(paginated.getPage() < paginated.getTotalPages());
        paginated.setHasPrevious(paginated.getPage() > 1);
        return HandleResponse.ok(paginated);
    }
}
