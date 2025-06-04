package com.shop.clothing.scheduleTask;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.payment.entity.enums.PaymentStatus;
import com.shop.clothing.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Component
public class PaymentTask {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Scheduled(fixedRate = 900000)
    @Transactional
    public void removeExpiredPayment() {
        System.out.println("Huỷ các yêu cầu thanh toán hết hạn");
        var order = orderRepository.getOrderByPaymentMethodInAndCreatedDateBeforeAndCompletedDateIsNull(
                List.of(new PaymentMethod[]{PaymentMethod.MOMO_QR, PaymentMethod.MOMO_ATM}),
                java.time.LocalDateTime.now().minusMinutes(15)
        );
        order.forEach(order1 -> {
            var payment = paymentRepository.findFirstByOrderIdSortedByCreatedDateDesc(order1.getOrderId());
            if (payment.isPresent()) {
                if (payment.get().getStatus() == com.shop.clothing.payment.entity.enums.PaymentStatus.PENDING) {
                    payment.get().setStatus(PaymentStatus.FAILED);
                    paymentRepository.save(payment.get());
                }
                paymentRepository.save(payment.get());
            }
        });
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cancelUnpaidOrder() {
        System.out.println("Huỷ các đơn hàng chưa thanh toán sau 1 ngày");
        var order = orderRepository.getOrderByPaymentMethodInAndCreatedDateBeforeAndCompletedDateIsNull(
                List.of(new PaymentMethod[]{PaymentMethod.MOMO_QR, PaymentMethod.MOMO_ATM}),
                java.time.LocalDateTime.now().minusDays(1)
        );
        order.forEach(order1 -> {
            var payment = paymentRepository.findFirstByOrderIdSortedByCreatedDateDesc(order1.getOrderId());
            if (payment.isPresent()) {
                if (payment.get().getStatus() == PaymentStatus.FAILED) {
                    payment.get().setStatus(com.shop.clothing.payment.entity.enums.PaymentStatus.CANCELLED);
                    paymentRepository.save(payment.get());
                    order1.setStatus(com.shop.clothing.order.entity.enums.OrderStatus.CANCELLED);
                    order1.setCancelReason("Không thanh toán được, huỷ bởi hệ thống");
                    orderRepository.save(order1);
                }
                paymentRepository.save(payment.get());
            }
        });
    }
}
