package com.software_system_development.arona_mysterious_shop_backend.service;

import com.software_system_development.arona_mysterious_shop_backend.model.dto.address.AddressAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductDeleteRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 29967
* @description 针对表【address】的数据库操作Service
* @createDate 2024-06-05 12:28:45
*/
public interface AddressService extends IService<Address> {
//    /**
//     * 增加地址
//     * @param addressAddRequest
//     * @return
//     */
//    int addAddress(AddressAddRequest addressAddRequest);
//
//    /**
//     * 修改地址信息
//     * @param productUpdateRequest
//     * @param request
//     * @return
//     */
//    int updateAddress(ProductUpdateRequest productUpdateRequest, HttpServletRequest request);
//
//    /**
//     * 删除商品
//     * @param deleteRequest
//     * @param request
//     * @return
//     */
//    boolean removeProduct(ProductDeleteRequest deleteRequest, HttpServletRequest request);
//
//    /**
//     *  获取商品信息
//     * @param id
//     * @param request
//     * @return
//     */
//    Product getProduct(int id, HttpServletRequest request);
//
//    /**
//     * 获取脱敏的用户信息列表
//     *
//     * @param userList 用户信息列表
//     * @return
//     */
//    List<UserVO> getUserVO(List<User> userList);
}
