function toReadablePaymentStatus(status) {
    switch (status) {
        case PaymentStatus.PENDING:
            return "Chờ thanh toán";
        case PaymentStatus.PAID:
            return "Đã thanh toán";
        case PaymentStatus.CANCELLED:
            return "Đã hủy";
        case PaymentStatus.REFUNDED:
            return "Đã hoàn tiền";
        case PaymentStatus.FAILED:
            return "Thanh toán thất bại";
    }
}

function toReadableOrderStatus(status) {
    switch (status) {
        case OrderStatus.PENDING:
            return "Chờ xử lý";
        case OrderStatus.PROCESSING:
            return "Đang xử lý";
        case OrderStatus.SHIPPING:
            return "Đang giao hàng";
        case OrderStatus.DELIVERED:
            return "Đã giao hàng";
        case OrderStatus.CANCELLED:
            return "Đã hủy";
        case OrderStatus.RETURNED:
            return "Đã trả hàng";
        case OrderStatus.REFUNDED:
            return "Đã hoàn tiền";
    }
}

function getListOrderStatus() {
    return [
        {id: OrderStatus.PENDING, name: "Chờ xử lý"},
        {id: OrderStatus.PROCESSING, name: "Đang xử lý"},
        {id: OrderStatus.SHIPPING, name: "Đang giao hàng"},
        {id: OrderStatus.DELIVERED, name: "Đã giao hàng"},
        {id: OrderStatus.CANCELLED, name: "Đã hủy"},
        {id: OrderStatus.RETURNED, name: "Đã trả hàng"},
        {id: OrderStatus.REFUNDED, name: "Đã hoàn tiền"},
    ];
}
function getListPaymentStatus() {
    return [
        {id: PaymentStatus.PENDING, name: "Chờ thanh toán"},
        {id: PaymentStatus.PAID, name: "Đã thanh toán"},
        {id: PaymentStatus.CANCELLED, name: "Đã hủy"},
        {id: PaymentStatus.REFUNDED, name: "Đã hoàn tiền"},
        {id: PaymentStatus.FAILED, name: "Thanh toán thất bại"},
    ];
}

function toReadablePaymentMethod(method) {
    console.log(method);
    switch (method) {
        case "MOMO_QR":
            return "Thanh toán qua Momo";
        case "MOMO_ATM":
            return "Thanh toán bằng thẻ ATM";
        default:
            return "Thanh toán khi nhận hàng";
    }
}

function getPaymentStatusClass(status) {
    switch (status) {
        case PaymentStatus.PENDING:
            return "bg-yellow-200 text-yellow-800";
        case PaymentStatus.PAID:
            return "bg-green-200 text-green-800";
        case PaymentStatus.CANCELLED:
            return "bg-red-200 text-red-800";
        case PaymentStatus.REFUNDED:
            return "bg-blue-200 text-blue-800";
        case PaymentStatus.FAILED:
            return "bg-red-200 text-red-800";
    }
}

function getOrderStatusClass(status) {
    switch (status) {
        case OrderStatus.PENDING:
            return "bg-yellow-200 text-yellow-800";
        case OrderStatus.PROCESSING:
            return "bg-blue-200 text-blue-800";
        case OrderStatus.SHIPPING:
            return "bg-purple-200 text-purple-800";
        case OrderStatus.DELIVERED:
            return "bg-green-200 text-green-800";
        case OrderStatus.CANCELLED:
            return "bg-red-200 text-red-800";
        case OrderStatus.RETURNED:
            return "bg-red-200 text-red-800";
        case OrderStatus.REFUNDED:
            return "bg-blue-200 text-blue-800";
    }
}