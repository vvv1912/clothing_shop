package com.shop.clothing.payment.command.cancelExpiredPayment;

import com.shop.clothing.common.Cqrs.IRequest;



public record CancelExpiredPaymentCommand() implements IRequest<Void> {
}
