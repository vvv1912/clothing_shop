package com.shop.clothing.order.entity;

import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.rating.Rating;
import com.shop.clothing.user.entity.User;
import com.shop.clothing.common.AuditableEntity;
import com.shop.clothing.promotion.Promotion;
import com.shop.clothing.order.entity.enums.OrderStatus;
import com.shop.clothing.payment.entity.Payment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "_order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order extends AuditableEntity {
    @Id
    @Column(updatable = false, nullable = false, name = "order_id", length = 36)
    private String orderId;

    @Column(name = "customer_name", length = 100)
    private String customerName;

    @Column(length = 500)
    private String address;
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "total_amount")
    private int totalAmount;

    @Column(length = 500)
    private String note;

    @Column(name = "delivery_fee")
    private double deliveryFee;

    @Column(name = "cancel_reason", length = 500)
    private String cancelReason;


    @Column()
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Payment> payments;

    @ManyToOne(fetch = FetchType.LAZY)
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    
    private java.util.List<OrderItem> orderItems;

    @Column(name = "completed_date")
    private java.time.LocalDateTime completedDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Rating> ratings;

}
