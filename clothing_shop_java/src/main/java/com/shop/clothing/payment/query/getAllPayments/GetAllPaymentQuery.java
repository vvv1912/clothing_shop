package com.shop.clothing.payment.query.getAllPayments;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.PaginationRequest;
import com.shop.clothing.common.dto.Paginated;
import com.shop.clothing.payment.dto.PaymentDto;
import com.shop.clothing.payment.entity.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetAllPaymentQuery extends PaginationRequest implements IRequest<Paginated<PaymentDto>> {
    private List<PaymentStatus> statuses = new ArrayList<>();
}
