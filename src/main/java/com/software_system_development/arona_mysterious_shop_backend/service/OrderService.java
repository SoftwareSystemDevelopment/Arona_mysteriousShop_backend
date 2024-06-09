package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderQueryRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Order;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.OrderItem;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.OrderVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderService extends IService<Order> {

    int addOrder(OrderAddRequest orderAddRequest, HttpServletRequest request);

    int updateOrder(OrderUpdateRequest orderUpdateRequest, HttpServletRequest request);

    boolean removeOrder(int orderId, HttpServletRequest request);

    OrderVO getOrder(int orderId, HttpServletRequest request);

    List<Order> list(HttpServletRequest request);

    List<OrderVO> getOrderVO(List<Order> orderList);

    QueryWrapper<Order> getQueryWrapper(OrderQueryRequest orderQueryRequest);
}
