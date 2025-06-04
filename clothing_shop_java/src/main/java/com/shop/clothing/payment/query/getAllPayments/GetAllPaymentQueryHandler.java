package com.shop.clothing.payment.query.getAllPayments;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.payment.dto.PaymentDto;
import com.shop.clothing.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetAllPaymentQueryHandler implements IRequestHandler<GetAllPaymentQuery, Paginated<PaymentDto>> {
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    @Override
    public HandleResponse<Paginated<PaymentDto>> handle(GetAllPaymentQuery query) {
        var payments = paymentRepository.findAllByStatusIn(query.getStatuses(), query.getPageable("createdDate"));
        var paymentDtos = payments.map(payment -> modelMapper.map(payment, PaymentDto.class));
        return HandleResponse.ok(Paginated.of(paymentDtos));
    }
}
