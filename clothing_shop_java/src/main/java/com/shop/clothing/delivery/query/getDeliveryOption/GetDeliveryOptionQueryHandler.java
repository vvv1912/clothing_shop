package com.shop.clothing.delivery.query.getDeliveryOption;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.delivery.GiaoHangNhanhService;
import com.shop.clothing.delivery.IDeliveryService;
import com.shop.clothing.delivery.config.GiaoHangNhanhConfig;
import com.shop.clothing.delivery.dto.GetValidShipServiceRequest;
import com.shop.clothing.delivery.dto.GetValidShipServiceResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetDeliveryOptionQueryHandler implements IRequestHandler<GetDeliveryOptionQuery, List<GetValidShipServiceResponse>> {
    private final IDeliveryService deliveryService;

    @Override
    public HandleResponse<List<GetValidShipServiceResponse>> handle(GetDeliveryOptionQuery query) {
        var request = GetValidShipServiceRequest.builder()
                .toCity(query.getToProvince())
                .toDistrict(query.getToDistrict())
                .orderValue(query.getOrderValue())
                .cod(query.getCod()).build();
        var response = deliveryService.getValidShipService(request);
        return HandleResponse.ok(response);
    }
}
