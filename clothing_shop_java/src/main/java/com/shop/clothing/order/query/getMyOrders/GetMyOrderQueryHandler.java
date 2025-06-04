package com.shop.clothing.order.query.getMyOrders;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.config.CurrentUserService;
import com.shop.clothing.order.dto.OrderDto;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.payment.dto.PaymentDto;
import com.shop.clothing.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class GetMyOrderQueryHandler implements IRequestHandler<GetMyOrderQuery, List<OrderDto>> {
    private final ModelMapper _mapper;
    private final OrderRepository _orderRepository;
    private final PaymentRepository _paymentRepository;
    private final CurrentUserService _currentUserService;

    @Override
    @Transactional(readOnly = true)
    public HandleResponse<List<OrderDto>> handle(GetMyOrderQuery getMyOrderQuery) throws Exception {
        var orders = _orderRepository.findAllByUserUserId(_currentUserService.getCurrentUserId().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cần đăng nhập để thực hiện chức năng này")
        ));
         orders.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
        var orderDtos = orders.stream().map(order -> {
            var orderDto = _mapper.map(order, OrderDto.class);
            var payment = _paymentRepository.findFirstByOrderIdSortedByCreatedDateDesc(order.getOrderId());
            payment.ifPresent(value -> orderDto.setLatestPayment(_mapper.map(value, PaymentDto.class)));
            return orderDto;
        }).toList();
        return HandleResponse.ok(orderDtos);
    }
}
