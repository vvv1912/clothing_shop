package com.shop.clothing.shop.command;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.shop.ShopSetting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UpdateShopInfoCommandHandler implements IRequestHandler<UpdateShopInfoCommand, Void> {
    private final ShopSetting shopSetting;

    @Override
    public HandleResponse<Void> handle(UpdateShopInfoCommand updateShopInfoCommand) throws Exception {
        shopSetting.setShopCity(updateShopInfoCommand.getShopCity());
        shopSetting.setShopDistrict(updateShopInfoCommand.getShopDistrict());
        shopSetting.setShopEmail(updateShopInfoCommand.getShopEmail());
//        shopSetting.setShopLogo(updateShopInfoCommand.getShopLogo());
        shopSetting.setShopName(updateShopInfoCommand.getShopName());
        shopSetting.setShopOwner(updateShopInfoCommand.getShopOwner());
        shopSetting.setShopPhone(updateShopInfoCommand.getShopPhone());
        shopSetting.setShopStreet(updateShopInfoCommand.getShopStreet());
        shopSetting.setShopWard(updateShopInfoCommand.getShopWard());
        if (shopSetting.save()) {
            return HandleResponse.ok();
        }
        return HandleResponse.error("Update shop info failed");
    }
}
