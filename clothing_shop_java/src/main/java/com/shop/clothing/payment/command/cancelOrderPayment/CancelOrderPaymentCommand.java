package com.shop.clothing.payment.command.cancelOrderPayment;

import com.shop.clothing.common.Cqrs.IRequest;

public record CancelOrderPaymentCommand(String orderId) implements IRequest<Void> {
}
