package com.shop.clothing.config.startup;


import com.shop.clothing.category.CategoryRepository;
import com.shop.clothing.category.command.createCategory.CreateCategoryCommand;
import com.shop.clothing.common.Cqrs.ISender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SeedCategory {
    private final CategoryRepository categoryRepository;
    private final ISender sender;

    public void seedCategory() {
        if (categoryRepository.count() != 0) {
            return;
        }
        var ao =sender.send(CreateCategoryCommand.builder()
                .name("Áo")
                .build());
        var quan = sender.send(CreateCategoryCommand.builder()
                .name("Quần")
                .build());
        var giay = sender.send(CreateCategoryCommand.builder()
                .name("Giày")
                .build());
        var tat = sender.send(CreateCategoryCommand.builder()
                .name("Tất")
                .build());
        var aoTankTop = sender.send(CreateCategoryCommand.builder()
                .name("Áo tank top")
                .parentId(ao.get())
                .build());
        var aoThun = sender.send(CreateCategoryCommand.builder()
                .name("Áo T-shirt")
                .parentId(ao.get())
                .build());
        var aoKhoac = sender.send(CreateCategoryCommand.builder()
                .name("Áo khoác")
                .parentId(ao.get())
                .build());
        var aoSoMi = sender.send(CreateCategoryCommand.builder()
                .name("Áo sơ mi")
                .parentId(ao.get())
                .build());
        var aoPolo = sender.send(CreateCategoryCommand.builder()
                .name("Áo Polo")
                .parentId(ao.get())
                .build());
        var aoTheThao = sender.send(CreateCategoryCommand.builder()
                .name("Áo thể thao")
                .parentId(ao.get())
                .build());
        var quanDai = sender.send(CreateCategoryCommand.builder()
                .name("Quần dài")
                .parentId(quan.get())
                .build());
        var quanShort = sender.send(CreateCategoryCommand.builder()
                .name("Quần short")
                .parentId(quan.get())
                .build());
        var quanJogger = sender.send(CreateCategoryCommand.builder()
                .name("Quần jogger")
                .parentId(quan.get())
                .build());
        var quanKaki = sender.send(CreateCategoryCommand.builder()
                .name("Quần kaki")
                .parentId(quan.get())
                .build());
        var quanJean = sender.send(CreateCategoryCommand.builder()
                .name("Quần jean")
                .parentId(quan.get())
                .build());
        var giayTheThao = sender.send(CreateCategoryCommand.builder()
                .name("Giày thể thao")
                .parentId(giay.get())
                .build());
        var giayTay = sender.send(CreateCategoryCommand.builder()
                .name("Giày tây")
                .parentId(giay.get())
                .build());
        var giayBoot = sender.send(CreateCategoryCommand.builder()
                .name("Giày boot")
                .parentId(giay.get())
                .build());
        var quanLot = sender.send(CreateCategoryCommand.builder()
                .name("Quần lót")
                .parentId(quan.get())
                .build());
        var quanNam = sender.send(CreateCategoryCommand.builder()
                .name("Quần nam")
                .parentId(quan.get())
                .build());
        var vay = sender.send(CreateCategoryCommand.builder()
                .name("Chân váy")
                .build());
        var dam = sender.send(CreateCategoryCommand.builder()
                .name("Đầm")
                .build());

    }
}
