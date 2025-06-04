package com.shop.clothing.delivery.goShip;

import com.shop.clothing.delivery.IAddressService;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class GoShipAddressService implements IAddressService {
    private GoShipProperties goShipProperties;

    @Override
    public int findProvinceId(String provinceName) {
        var restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        var accessToken = goShipProperties.getAccessToken();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                goShipProperties.getEndpoint() + "/cities",
                HttpMethod.GET,
                entity,
                String.class
        );
        JSONObject obj = new JSONObject(response.getBody());
        JSONArray data = obj.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject province = data.getJSONObject(i);
            var fromDataLowerCase = province.getString("name").toLowerCase();
            var provinceNameLowerCase = provinceName.toLowerCase();
            if (fromDataLowerCase.endsWith(provinceNameLowerCase) || provinceNameLowerCase.endsWith(fromDataLowerCase)) {
                return province.getInt("id");
            }
        }
        return 0;
    }

    @Override
    public int findDistrictId(String districtName, int provinceId) {

        var restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        var accessToken = goShipProperties.getAccessToken();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                goShipProperties.getEndpoint() + "/cities/" + provinceId + "/districts",
                HttpMethod.GET,
                entity,
                String.class
        );
        JSONObject obj = new JSONObject(response.getBody());
        JSONArray data = obj.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject district = data.getJSONObject(i);
            var fromDataLowerCase = district.getString("name").toLowerCase();
            var districtNameLowerCase = districtName.toLowerCase();
            if (fromDataLowerCase.endsWith(districtNameLowerCase) || districtNameLowerCase.endsWith(fromDataLowerCase)) {
                return district.getInt("id");
            }
        }
        return 0;
    }

    @Override
    public int findWardId(String wardName, int districtId) {
        var restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        var accessToken = goShipProperties.getAccessToken();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                goShipProperties.getEndpoint() + "/districts/" + districtId + "/wards",
                HttpMethod.GET,
                entity,
                String.class
        );
        JSONObject obj = new JSONObject(response.getBody());
        JSONArray data = obj.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject ward = data.getJSONObject(i);
            var fromDataLowerCase = ward.getString("name").toLowerCase();
            var wardNameLowerCase = wardName.toLowerCase();
            if (fromDataLowerCase.endsWith(wardNameLowerCase) || wardNameLowerCase.endsWith(fromDataLowerCase)) {
                return ward.getInt("id");
            }
        }
        return 0;

    }
}
