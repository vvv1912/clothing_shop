package com.shop.clothing.promotion;

import com.shop.clothing.common.AuditableEntity;
import com.shop.clothing.payment.entity.enums.PromotionType;
import com.shop.clothing.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table

public class Promotion extends AuditableEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false,name = "promotion_id")
    private int promotionId;

    @Column(unique = true, nullable = false,length = 100)
    private String code;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private int discount;

    @Column(nullable = false)
    private PromotionType type;

    @Column()
    private int minOrderAmount;

    @Column( nullable = true)
    private Integer maxValue;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private int stock;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private java.util.List<Order> orders;



    public int getFinalDiscount(int totalPrice) {
        if (type == PromotionType.PERCENTAGE) {
            int reduce = totalPrice * discount / 100;
            if (reduce > maxValue) {
                return maxValue;
            } else {
                return reduce;
            }

        } else {
            if (discount > totalPrice) {
                return totalPrice;
            } else {
                return discount;
            }
        }
    }
}
