package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderUpdateRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 29967
* @description 针对表【order】的数据库操作Service
* @createDate 2024-06-05 12:28:45
*/
public interface OrderService extends IService<Order> {
    int addOrder(OrderAddRequest orderAddRequest, HttpServletRequest request);

    int updateOrder(OrderUpdateRequest orderUpdateRequest, HttpServletRequest request);

    boolean removeOrder(int orderId, HttpServletRequest request);

    Order getOrder(int orderId, HttpServletRequest request);

    Page<Order> listOrdersByPage(Page<Order> page, QueryWrapper<Order> queryWrapper, HttpServletRequest request);

    void placeOrder(int orderId, HttpServletRequest request);

    /**
     * 获取购物车中的所有产品信息
     * @param cartId
     * @return 购物车中的所有产品信息列表
     */
    List<CartProduct> getCartProducts(Integer cartId);
}
