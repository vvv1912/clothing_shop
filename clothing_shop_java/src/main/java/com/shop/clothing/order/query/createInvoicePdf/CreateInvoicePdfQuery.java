package com.shop.clothing.order.query.createInvoicePdf;

import com.shop.clothing.common.Cqrs.IRequest;

public record CreateInvoicePdfQuery(String orderId) implements IRequest<String> {

}
