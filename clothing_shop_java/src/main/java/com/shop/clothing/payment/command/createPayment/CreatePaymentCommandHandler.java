package com.shop.clothing.payment.command.createPayment;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.order.entity.enums.OrderStatus;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.payment.PaymentStrategyFactory;
import com.shop.clothing.payment.dto.CreatePaymentResponse;
import com.shop.clothing.payment.entity.enums.PaymentStatus;
import com.shop.clothing.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreatePaymentCommandHandler implements IRequestHandler<CreatePaymentCommand, CreatePaymentResponse> {
    private final PaymentStrategyFactory paymentStrategyFactory;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    @Override
    public HandleResponse<CreatePaymentResponse> handle(CreatePaymentCommand createPaymentCommand) throws Exception {

        var order = orderRepository.findById(createPaymentCommand.getOrderId()).orElse(null);
        if (order == null) {
            return HandleResponse.error("Đơn hàng không tồn tại");
        }
        var lastPayment = paymentRepository.findFirstByOrderIdSortedByCreatedDateDesc(createPaymentCommand.getOrderId()).orElse(null);

        if(lastPayment != null && lastPayment.getStatus() == PaymentStatus.PAID){
            return HandleResponse.error("Đơn hàng đã được thanh toán");
        }

        if(lastPayment != null &&lastPayment.getOrder().getStatus()== OrderStatus.CANCELLED){
            return HandleResponse.error("Đơn hàng đã bị hủy");
        }

        var paymentStrategy = paymentStrategyFactory.getPaymentStrategy(order.getPaymentMethod());
        var response = paymentStrategy.createPayment(createPaymentCommand.getOrderId());
        return HandleResponse.ok(response);
    }
}
