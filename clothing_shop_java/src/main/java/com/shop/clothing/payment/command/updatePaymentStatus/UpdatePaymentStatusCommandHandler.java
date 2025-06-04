package com.shop.clothing.payment.command.updatePaymentStatus;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UpdatePaymentStatusCommandHandler implements IRequestHandler<UpdatePaymentStatusCommand, Void> {
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public HandleResponse<Void> handle(UpdatePaymentStatusCommand updatePaymentStatusCommand) throws Exception {
        var payment = paymentRepository.findById(updatePaymentStatusCommand.paymentId());
        if (payment.isEmpty()) {
            return HandleResponse.error("Không tìm thấy thanh toán", HttpStatus.NOT_FOUND);
        }
        payment.get().setStatus(updatePaymentStatusCommand.status());
        paymentRepository.save(payment.get());
        return HandleResponse.ok();
    }
}
