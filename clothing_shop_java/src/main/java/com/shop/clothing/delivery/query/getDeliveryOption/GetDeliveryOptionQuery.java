package com.shop.clothing.delivery.query.getDeliveryOption;

import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.delivery.dto.GetValidShipServiceResponse;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class GetDeliveryOptionQuery implements IRequest<List<GetValidShipServiceResponse>> {
    @Length(min = 10, max = 200, message = "Địa chỉ nhận hàng không hợp lệ")
    public String toAddress;
    @Min(value = 0, message = "Giá trị đơn hàng không hợp lệ")
    public int orderValue;
    @Builder.Default
    public int cod = 0;
    @Builder.Default
    public int widthInCm = 20;
    @Builder.Default
    public int heightInCm = 5;
    @Builder.Default
    public int lengthInCm = 20;
    @Builder.Default
    public int weightInGram = 300;

    private String getProvince(String rawAddress) {
        String[] split = rawAddress.split(",");
        return split[split.length - 1].trim();
    }

    private String getDistrict(String rawAddress) {
        String[] split = rawAddress.split(",");
        return split[split.length - 2].trim();
    }

    private String getWard(String rawAddress) {
        String[] split = rawAddress.split(",");
        return split[split.length - 3].trim();
    }

    private String getDetailAddress(String rawAddress) {
        String[] split = rawAddress.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length - 3; i++) {
            stringBuilder.append(split[i]);
        }
        return stringBuilder.toString().trim();
    }

    public String getToProvince() {
        return getProvince(toAddress);
    }

    public String getToDistrict() {
        return getDistrict(toAddress);
    }

    public String getToWard() {
        return getWard(toAddress);
    }

    public String getToDetailAddress() {
        return getDetailAddress(toAddress);
    }
}
