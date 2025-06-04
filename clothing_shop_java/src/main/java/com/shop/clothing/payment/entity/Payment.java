package com.shop.clothing.payment.entity;

import com.shop.clothing.common.AuditableEntity;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.payment.entity.enums.PaymentStatus;
import com.shop.clothing.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "payment")
public class Payment extends AuditableEntity {
    @Id
    @Column(updatable = false, nullable = false, name = "payment_id", length = 36)
    private String paymentId;
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;
    private int amount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    private LocalDateTime completedDate;

}
