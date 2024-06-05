package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.address.AddressAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Address;
import com.software_system_development.arona_mysterious_shop_backend.service.AddressService;
import com.software_system_development.arona_mysterious_shop_backend.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【address】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

//    @Override
//    public int addAddress(AddressAddRequest addressAddRequest) {
//        Address address = new Address();
//        address.setAddressName(addressAddRequest.getAddressName();
//        boolean result = save(address);
//        if (!result) {
//            throw new BusinessException(ErrorCode.OPERATION_ERROR);
//        }
//        return address.getAddressAreaId();
//    }
}




