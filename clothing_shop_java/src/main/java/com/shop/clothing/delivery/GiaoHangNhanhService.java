package com.shop.clothing.delivery;

import com.shop.clothing.delivery.config.GiaoHangNhanhConfig;
import com.shop.clothing.delivery.dto.GHNResponse;
import com.shop.clothing.delivery.dto.GetProvinceResponse;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class GiaoHangNhanhService  {
    private final GiaoHangNhanhConfig giaoHangNhanhConfig;

    public int findProvinceId(String provinceName) {
        var restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", giaoHangNhanhConfig.getApiKey());
        headers.set("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province",
                HttpMethod.GET,
                entity,
                String.class
        );
        JSONObject obj = new JSONObject(response.getBody());
        JSONArray data = obj.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject province = data.getJSONObject(i);
            if (province.getString("ProvinceName").toLowerCase().endsWith(provinceName.toLowerCase())) {
                return province.getInt("ProvinceID");
            }
        }
        return 0;

    }

    public int findDistrictId(String districtName, int provinceId) {
//        https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district
        var restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", giaoHangNhanhConfig.getApiKey());
        headers.set("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("province_id", provinceId);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district",
                HttpMethod.POST,
                entity,
                String.class
        );
        JSONObject obj = new JSONObject(response.getBody());
        JSONArray data = obj.getJSONArray("data");

        for (int i = 0; i < data.length(); i++) {
            JSONObject district = data.getJSONObject(i);
            if (district.getString("DistrictName").toLowerCase().endsWith(districtName.toLowerCase())) {
                return district.getInt("DistrictID");
            }
        }
        return 0;
    }

    public int findWardId(String wardName, int districtId) {
        var restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", giaoHangNhanhConfig.getApiKey());
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + districtId,
                HttpMethod.GET,
                entity,
                String.class
        );
        JSONObject obj = new JSONObject(response.getBody());
        JSONArray data = obj.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject ward = data.getJSONObject(i);
            if (ward.getString("WardName").toLowerCase().endsWith(wardName.toLowerCase())) {
                return ward.getInt("WardCode");
            }
        }

        return 0;
    }

    public int getDeliveryFee(String toProvince, String toDistrict, String toWard, int total, int weight) {
//
        var restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", giaoHangNhanhConfig.getApiKey());
        headers.set("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("shop_id", giaoHangNhanhConfig.getShopId());
        body.put("service_type_id", 2);
        var provinceId = findProvinceId(toProvince);
        var districtId = findDistrictId(toDistrict, provinceId);
        var wardId = findWardId(toWard, districtId);
        body.put("to_district_id", districtId);
        body.put("to_ward_code", wardId + "");
        body.put("weight", weight == 0 ? 600 : weight);
        body.put("insurance_value", total);
        body .put("cod_failed_amount", 0);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee",
                HttpMethod.POST,
                entity,
                String.class
        );
        JSONObject obj = new JSONObject(response.getBody());
        JSONObject data = obj.getJSONObject("data");
        return data.getInt("total");

    }
}
