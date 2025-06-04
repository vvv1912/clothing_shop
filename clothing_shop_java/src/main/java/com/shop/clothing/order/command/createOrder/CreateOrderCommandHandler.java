package com.shop.clothing.order.command.createOrder;

import com.shop.clothing.cart.CartRepository;
import com.shop.clothing.common.BusinessLogicException;
import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.common.util.ClientUtil;
import com.shop.clothing.config.ICurrentUserService;
import com.shop.clothing.delivery.IDeliveryService;
import com.shop.clothing.delivery.dto.CreateShipOrderRequest;
import com.shop.clothing.delivery.dto.GetValidShipServiceRequest;
import com.shop.clothing.delivery.query.getDeliveryFee.GetDeliveryFeeQuery;
import com.shop.clothing.mail.MailService;
import com.shop.clothing.order.entity.Order;
import com.shop.clothing.order.entity.OrderItem;
import com.shop.clothing.order.repository.OrderItemRepository;
import com.shop.clothing.order.repository.OrderRepository;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.product.entity.ProductOption;
import com.shop.clothing.product.repository.ProductOptionRepository;
import com.shop.clothing.promotion.Promotion;
import com.shop.clothing.promotion.query.checkPromotion.CheckPromotionQuery;
import com.shop.clothing.promotion.repository.PromotionRepository;
import com.shop.clothing.user.entity.User;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class CreateOrderCommandHandler implements IRequestHandler<CreateOrderCommand, String> {
    private final ICurrentUserService currentUserService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PromotionRepository promotionRepository;
    private final ISender sender;
    private final ProductOptionRepository productOptionRepository;
    private final ClientUtil clientUtil;
    private final IDeliveryService deliveryService;
    private final MailService mailService;

    @Override
    @Transactional(rollbackFor = {BusinessLogicException.class, Throwable.class})
    public HandleResponse<String> handle(CreateOrderCommand createOrderCommand) {

        var productOptionsThatWillBuy = productOptionRepository.findAllByProductOptionIdIn(createOrderCommand.getOrderItems().stream().map(CreateOrderCommand.OrderItem::getProductOptionId).toList());
        Promotion promotion = null;
        if (!createOrderCommand.getPromotionCode().isBlank()) {
            promotion = promotionRepository.findByCodeIgnoreCase(createOrderCommand.getPromotionCode()).orElse(null);
        }

        Map<Integer, Integer> productOptionIdToQuantity = createOrderCommand.getOrderItems().stream().collect(
                java.util.stream.Collectors.toMap(CreateOrderCommand.OrderItem::getProductOptionId, CreateOrderCommand.OrderItem::getQuantity));
        Map<Integer, Integer> productOptionIdToFinalPrice = new HashMap<>();
        var totalPrice = productOptionsThatWillBuy.stream().mapToInt(productOption -> {
                    var quantity = productOptionIdToQuantity.get(productOption.getProductOptionId());
                    productOptionIdToFinalPrice.put(productOption.getProductOptionId(), productOption.getProduct().getFinalPrice());
                    return productOption.getProduct().getFinalPrice() * quantity;
                }
        ).sum();
        if (promotion != null) {
            var checkPromotion = sender.send(new CheckPromotionQuery(promotion.getCode(), totalPrice));
            if (!checkPromotion.hasError()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, checkPromotion.getError());
            }
            totalPrice = totalPrice - promotion.getFinalDiscount(totalPrice);
            promotion.setStock(promotion.getStock() - 1);
            promotionRepository.save(promotion);
        }

        var address = clientUtil.from(createOrderCommand.getAddress());
        int fee = 0;
        var orderId = UUID.randomUUID().toString();
        var validShipService = deliveryService.getValidShipService(GetValidShipServiceRequest.builder()
                .orderValue(totalPrice)
                .cod(createOrderCommand.getPaymentMethod() == PaymentMethod.COD ? (int) totalPrice : 0)
                .toCity(address.city)
                .toDistrict(address.district)
                .build());
        var chooseShipService = validShipService.stream().filter(shipService -> shipService.getId().trim().equalsIgnoreCase(createOrderCommand.getShipServiceId().trim())).findFirst();
        if (chooseShipService.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dịch vụ vận chuyển không hợp lệ");
        }
        fee = chooseShipService.get().getTotalFree();
        var createDeliveryOrder = CreateShipOrderRequest.builder()
                .orderAmount(totalPrice)
                .orderID(orderId)
                .cod(createOrderCommand.getPaymentMethod() == PaymentMethod.COD ? totalPrice + fee : 0)
                .rateServiceId(createOrderCommand.getShipServiceId())
                .toName(createOrderCommand.getCustomerName())
                .toAddress(createOrderCommand.getAddress())
                .toPhone(createOrderCommand.getPhoneNumber())
                .build();
//        var deliveryOrder = deliveryService.createOrder(createDeliveryOrder);

        var newOrder = Order.builder().orderId(orderId)
                .address(createOrderCommand.getAddress())
                .customerName(createOrderCommand.getCustomerName())
                .phoneNumber(createOrderCommand.getPhoneNumber())
                .note(createOrderCommand.getNote())
                .user((User) currentUserService.getCurrentUser().orElse(null))
                .promotion(promotion)
                .email(createOrderCommand.getEmail())
                .payments(null)
                .deliveryFee(fee)
                .totalAmount((int) totalPrice + fee)
                .paymentMethod(createOrderCommand.getPaymentMethod())
                .build();
        orderRepository.save(newOrder);
        productOptionIdToQuantity.forEach((productOptionId, quantity) -> {
            var orderItem = OrderItem.builder()
                    .orderId(newOrder.getOrderId())
                    .productOptionId(productOptionId)
                    .quantity(quantity)
                    .price(productOptionIdToFinalPrice.get(productOptionId))
                    .build();

            orderItemRepository.save(orderItem);
            var productOption = productOptionRepository.findById(productOptionId).orElseThrow();
            if (productOption.getStock() < quantity) {
                // rollback
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Số lượng sản phẩm" + productOption.getProduct().getName() + " không đủ");
            }
            productOption.setStock(productOption.getStock() - quantity);
            productOptionRepository.save(productOption);
        });
        CompletableFuture.runAsync(() -> {
            try {
                mailService.sendEmail(newOrder.getEmail(), "Đặt hàng thành công", "<h1>Đặt hàng thành công</h1> <p>Mã đơn hàng của bạn là: " + newOrder.getOrderId() + "</p>");
            } catch (MessagingException e) {
                System.err.println("Cannot send email");
            }
        });
        return HandleResponse.ok(newOrder.getOrderId());
    }
}
