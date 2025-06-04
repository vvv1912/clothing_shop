package com.shop.clothing.payment.command.cancelExpiredPayment;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CancelExpiredPaymentCommandHandler implements IRequestHandler<CancelExpiredPaymentCommand, Void> {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    @Override
    @Transactional
    public HandleResponse<Void> handle(CancelExpiredPaymentCommand cancelExpiredPaymentCommand) throws Exception {
        var listExpiredPayment = paymentRepository.getAllByStatusAndCreatedDateBeforeAndOrderCompletedDateIsNull(
                com.shop.clothing.payment.entity.enums.PaymentStatus.PENDING,
                java.time.LocalDateTime.now().minusMinutes(15)
        );
        System.out.println("Có " + listExpiredPayment.size() + " đơn hàng hết hạn thanh toán");
        listExpiredPayment.forEach(payment -> {
            payment.setStatus(com.shop.clothing.payment.entity.enums.PaymentStatus.CANCELLED);
            paymentRepository.save(payment);
            var order = payment.getOrder();
            order.setStatus(com.shop.clothing.order.entity.enums.OrderStatus.CANCELLED);
            order.setCancelReason("Hết hạn thanh toán");
            orderRepository.save(order);
        });
        return HandleResponse.ok();
    }
}
