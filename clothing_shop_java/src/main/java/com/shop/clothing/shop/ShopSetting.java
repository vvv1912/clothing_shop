package com.shop.clothing.shop;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
@Getter
@Setter
public class ShopSetting {
    private static String path = "shopSetting.json";
    private String shopName;
    private String shopPhone;
    private String shopEmail;
    private String shopCity;
    private String shopWard;
    private String shopDistrict;
    private String shopStreet;
    private String shopOwner;
    private String shopLogo;

    public static class SliderBanner {
        public String image;
        public String href;
    }

    public List<SliderBanner> sliderBanner;

    public ShopSetting() {

        try {
            Resource resource = new ClassPathResource(path);
            File file = resource.getFile();
            if (file.exists()) {
                String json = Files.readString(file.toPath());
                JSONObject jsonObject = new JSONObject(json);

                this.shopName = jsonObject.getString("shopName");
                this.shopPhone = jsonObject.getString("shopPhone");
                this.shopEmail = jsonObject.getString("shopEmail");
                this.shopCity = jsonObject.getString("shopCity");
                this.shopWard = jsonObject.getString("shopWard");
                this.shopDistrict = jsonObject.getString("shopDistrict");
                this.shopStreet = jsonObject.getString("shopStreet");
                this.shopOwner = jsonObject.getString("shopOwner");
                this.shopLogo = jsonObject.getString("shopLogo");
                this.sliderBanner = new ArrayList<>();
                var sliderBannerJson = jsonObject.getJSONArray("sliderBanner");
                for (int i = 0; i < sliderBannerJson.length(); i++) {
                    var sliderBanner = new SliderBanner();
                    sliderBanner.image = sliderBannerJson.getJSONObject(i).getString("image");
                    sliderBanner.href = sliderBannerJson.getJSONObject(i).getString("href");
                    this.sliderBanner.add(sliderBanner);
                }
            } else {
                System.err.println("Không tìm thấy file shopSetting.json");
                System.exit(1);
            }
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public boolean save() throws JsonProcessingException {
        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(this);

        try {
            Resource resource = new ClassPathResource(path);
            File file = resource.getFile();
            Files.writeString(file.toPath(), json, StandardCharsets.UTF_8);

        } catch (IOException e) {
            return false;
        }
        return true;
    }


}
