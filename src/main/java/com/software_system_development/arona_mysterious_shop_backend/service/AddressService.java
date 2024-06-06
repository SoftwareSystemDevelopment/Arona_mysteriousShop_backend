package com.software_system_development.arona_mysterious_shop_backend.service;

import com.software_system_development.arona_mysterious_shop_backend.model.dto.address.AddressAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.address.AddressDeleteRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Address;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 29967
* @description 针对表【address】的数据库操作Service
* @createDate 2024-06-05 12:28:45
*/
public interface AddressService extends IService<Address> {
    int addAddress(AddressAddRequest addressAddRequest);
    boolean deleteAddress(AddressDeleteRequest addressDeleteRequest, HttpServletRequest request);
    List<Address> getAddressByUserId(Integer userId);
}
