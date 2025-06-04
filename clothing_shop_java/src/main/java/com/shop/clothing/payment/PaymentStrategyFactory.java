package com.shop.clothing.payment;

import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.payment.strategy.CODPaymentStrategy;
import com.shop.clothing.payment.strategy.MomoATMPaymentStrategy;
import com.shop.clothing.payment.strategy.MomoQrPaymentStrategy;
import com.shop.clothing.payment.strategy.PaymentStrategy;
import lombok.AllArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentStrategyFactory {
    private final ConfigurableApplicationContext context;

    public PaymentStrategy getPaymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case MOMO_QR -> context.getBean(MomoQrPaymentStrategy.class, PaymentStrategy.class);
            case MOMO_ATM -> context.getBean(MomoATMPaymentStrategy.class, PaymentStrategy.class);
            case COD -> context.getBean(CODPaymentStrategy.class, PaymentStrategy.class);
            default -> throw new RuntimeException("Không tìm thấy phương thức thanh toán");
        };
    }
}
