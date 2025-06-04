package com.shop.clothing.common.util;


import com.shop.clothing.category.CategoryDetailDto;
import com.shop.clothing.category.query.getAllCategoriesGroupByParentQuery.GetAllCategoriesGroupByParentQuery;
import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.order.entity.enums.OrderStatus;
import com.shop.clothing.payment.entity.enums.PaymentMethod;
import com.shop.clothing.payment.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@AllArgsConstructor

public class ClientUtil {
    private final ISender sender;

    public String toJsonString(Object object) {
        return new JSONObject(object).toString();
    }

    public String toVietnameseCurrency(int price) {
        return String.format("%,d", price).replace(',', '.') + "đ";
    }

    public String toVietnameseCurrency(double price) {
        int priceInt = (int) price;
        return String.format("%,d", priceInt).replace(',', '.') + "đ";
    }

    public String toVietnameseCurrency(float price) {
        int priceInt = (int) price;
        return String.format("%,d", priceInt).replace(',', '.') + "đ";
    }

    @Caching(cacheable = {
            @Cacheable(value = "homePage", key = "'categories'"),
    })
    public List<CategoryDetailDto> getCategoryTree() {
        return sender.send(new GetAllCategoriesGroupByParentQuery()).get();
    }

    @AllArgsConstructor
    public static class Address {
        public String ward;
        public String district;
        public String city;
        public String detail;
    }

    public Address from(String address) {
        var addressList = address.split(",");
        var ward = addressList[addressList.length - 3].trim();
        var district = addressList[addressList.length - 2].trim();
        var city = addressList[addressList.length - 1].trim();
        // 0 to length - 3 is detail
        StringBuilder detail = new StringBuilder();
        for (int i = 0; i < addressList.length - 3; i++) {
            detail.append(addressList[i].trim()).append(", ");
        }
        return new Address(ward, district, city, detail.toString());
    }

    private DateTimeFormatter dateFormatter() {

        return DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy");

    }

    public String formatDateTime(Date date) {
        return dateFormatter().format(date.toInstant());
    }

    public String formatDateTime(LocalDateTime date) {
        return dateFormatter().format(date);
    }

    public String toReadableString(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case MOMO_QR -> "Thanh toán qua ví MoMo";
            case MOMO_ATM -> "Thanh toán qua thẻ ATM";
            default -> "Thanh toán khi nhận hàng";
        };
    }

    public String toReadableString(OrderStatus status) {
        return switch (status) {
            case CANCELLED -> "Đã hủy";
            case DELIVERED -> "Đã giao";
            case PROCESSING -> "Đang xử lý";
            case REFUNDED -> "Đã hoàn tiền";
            case SHIPPING -> "Đang giao";
            case RETURNED -> "Đã trả hàng";
            case PENDING -> "Đang chờ xử lý";
        };
    }

    public String toReadableString(PaymentStatus status) {
        return switch (status) {
            case CANCELLED -> "Đã hủy";
            case PAID -> "Đã thanh toán";
            case REFUNDED -> "Đã hoàn tiền";
            case PENDING -> "Đang chờ xử lý";
            case FAILED -> "Thanh toán thất bại";
            case WAITING_FOR_REFUND -> "Đang chờ hoàn tiền";
        };
    }

    public OrderStatus[] orderStatuses() {
        return OrderStatus.values();
    }
    public PaymentStatus[] paymentStatuses() {
        return PaymentStatus.values();
    }
}
