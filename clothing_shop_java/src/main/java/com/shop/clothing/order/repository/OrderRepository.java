package com.shop.clothing.order.repository;

import com.shop.clothing.order.entity.Order;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import jakarta.persistence.Tuple;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByUserUserId(String userId);

    @Query(value = "SELECT DATE(o.completed_date) AS date, CAST(COUNT(DISTINCT(o.order_id)) as UNSIGNED) AS totalOrder, CAST(SUM(o.total_amount) as UNSIGNED )AS totalRevenue , CAST(SUM(oi.quantity) as UNSIGNED) AS totalQuantitySold FROM _order o " +
            "JOIN order_item oi ON o.order_id = oi.order_id " +
            "WHERE o.completed_date is not null" +
            " AND  (?1 is null or o.completed_date >= ?1) " +
            "AND (?2 is null or o.completed_date <= ?2) " +
            "GROUP BY DATE(o.completed_date) ORDER BY DATE(o.completed_date) DESC ", nativeQuery = true)
    // hibernate query
    List<Tuple> getSoldReport(Date startDate, Date endDate);

    List<Order> getOrderByPaymentMethodInAndCreatedDateBeforeAndCompletedDateIsNull(Collection<PaymentMethod> paymentMethod, LocalDateTime createdDate);

}
