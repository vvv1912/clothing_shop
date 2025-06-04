package com.shop.clothing.delivery;

public interface IAddressService {
     int findProvinceId(String provinceName) ;
     int findDistrictId(String districtName, int provinceId) ;
     int findWardId(String wardName, int districtId) ;
}
