package com.shop.clothing.payment.command.updatePaymentStatus;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.payment.entity.enums.PaymentStatus;

public record UpdatePaymentStatusCommand(String paymentId, PaymentStatus status) implements IRequest<Void> {
}
