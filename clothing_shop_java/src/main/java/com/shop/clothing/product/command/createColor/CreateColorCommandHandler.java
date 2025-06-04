package com.shop.clothing.product.command.createColor;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.product.entity.Color;
import com.shop.clothing.product.repository.ColorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreateColorCommandHandler implements IRequestHandler<CreateColorCommand, Integer> {
    private final ColorRepository colorRepository;

    @Override
    @Transactional
    public HandleResponse<Integer> handle(CreateColorCommand createColorCommand) {
        var existWithName = colorRepository.findByNameIgnoreCase(createColorCommand.getName());
        if (existWithName.isPresent()) {
            return HandleResponse.error("Tên màu đã tồn tại");
        }
        var color = new Color();
        color.setName(createColorCommand.getName());
        color.setImage(createColorCommand.getImage());
        colorRepository.save(color);
        return HandleResponse.ok(color.getColorId());

    }
}
