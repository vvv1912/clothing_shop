package com.shop.clothing.payment.command.cancelOrderPayment;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CancelOrderPaymentCommandHandler implements IRequestHandler<CancelOrderPaymentCommand, Void> {
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(CancelOrderPaymentCommand cancelOrderPaymentCommand) throws Exception {
        var payment = paymentRepository.findFirstByOrderIdSortedByCreatedDateDesc(cancelOrderPaymentCommand.orderId());
        if (payment.isEmpty()) {
            return HandleResponse.error("Không tìm thấy thanh toán");
        }
        payment.get().setStatus(com.shop.clothing.payment.entity.enums.PaymentStatus.CANCELLED);
        paymentRepository.save(payment.get());
        return HandleResponse.ok();

    }
}
