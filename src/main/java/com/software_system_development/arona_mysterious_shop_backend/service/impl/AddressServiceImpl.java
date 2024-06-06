package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.AddressMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.address.AddressAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.address.AddressDeleteRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Address;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.AddressService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 29967
* @description 针对表【address】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

    @Resource
    UserService userService;

    @Override
    public int addAddress(AddressAddRequest addressAddRequest) {
        if (addressAddRequest == null || addressAddRequest.getAddressName() == null || addressAddRequest.getUserId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 检查UserId 是否存在
        if (userService.getById(addressAddRequest.getUserId()) == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "指定的用户ID不存在");
        }

        Address address = new Address();
        BeanUtils.copyProperties(addressAddRequest, address);
        boolean saveResult = this.save(address);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加地址失败");
        }

        return address.getAddressAreaId();
    }

    @Override
    public boolean deleteAddress(AddressDeleteRequest addressDeleteRequest, HttpServletRequest request) {
        if (addressDeleteRequest == null || addressDeleteRequest.getAddressAreaId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }

        Address address = this.getById(addressDeleteRequest.getAddressAreaId());
        if (address == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "地址未找到");
        }

        // 从请求中获取当前用户信息
        UserVO loginUser = userService.getUserVO(request);

        // 验证用户权限
        if (loginUser.getUserId() != address.getAddressUserId()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限删除");
        }
        boolean removeResult = this.removeById(addressDeleteRequest.getAddressAreaId());
        if (!removeResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除地址失败");
        }
        return true;
    }


    @Override
    public List<Address> getAddressByUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("usertId", userId);
        List<Address> addressList = this.list(queryWrapper);
        if (addressList == null || addressList.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到对应地址");
        }

        return addressList;
    }
}




