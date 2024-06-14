package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.address.AddressAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.address.AddressUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Address;
import com.software_system_development.arona_mysterious_shop_backend.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@Slf4j
@Tag(name = "地址接口")
public class addressController {
    @Resource
    AddressService addressService;

    /**
     * 增加地址
     *
     * @param addressAddRequest 地址添加请求
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @PostMapping("/add")
    @Operation(summary = "增加地址")
    public BaseResponse<Integer> addAddress(@RequestBody AddressAddRequest addressAddRequest, HttpServletRequest request) {
        if (addressAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = addressService.addAddress(addressAddRequest, request);
        return ResultUtils.success(result);
    }

    /**
     * 删除地址
     *
     * @param addressId 地址ID
     * @param request HttpServletRequest
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除地址")
    public BaseResponse<Boolean> deleteAddress(@RequestParam Integer addressId, HttpServletRequest request) {
        if (addressId == null || addressId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        boolean result = addressService.deleteAddress(addressId, request);
        return ResultUtils.success(result);
    }

    @PostMapping
    @Operation(summary = "修改地址信息")
    public BaseResponse<Integer> updateAddress(@RequestBody AddressUpdateRequest addressUpdateRequest, HttpServletRequest request) {
        if (addressUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        int result = addressService.updateAddress(addressUpdateRequest, request);
        return ResultUtils.success(result);
    }

    /**
     * 查询某用户下的所有地址
     *
     * @return {@link BaseResponse}<{@link List}<{@link Address}>>
     */
    @GetMapping("/list")
    @Operation(summary = "查询用户的所有地址")
    public BaseResponse<List<Address>> listComments(HttpServletRequest request) {
        List<Address> addressList = addressService.getAddressByUserId(request);
        return ResultUtils.success(addressList);
    }
}
