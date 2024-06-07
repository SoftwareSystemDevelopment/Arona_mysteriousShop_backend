package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.OrderMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Order;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import com.software_system_development.arona_mysterious_shop_backend.service.OrderService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private UserService userService;

    @Resource
    CartService cartService;

    @Override
    public int addOrder(OrderAddRequest orderAddRequest, HttpServletRequest request) {
        Order order = new Order();
        BeanUtils.copyProperties(orderAddRequest, order);
        order.setOrderStatus(0); // 订单状态统一设置为0
        order.setOrderPayDate(DateTime.now());
        boolean result = save(order);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return order.getOrderId();
    }

    @Override
    public int updateOrder(OrderUpdateRequest orderUpdateRequest, HttpServletRequest request) {
        UserVO loginUser = userService.getUserVO(request);
        Order order = getById(orderUpdateRequest.getOrderId());
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (!order.getOrderUserId().equals(loginUser.getUserId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限修改订单");
        }
        BeanUtils.copyProperties(orderUpdateRequest, order);
        boolean result = updateById(order);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return order.getOrderId();
    }

    @Override
    public boolean removeOrder(int orderId, HttpServletRequest request) {
        UserVO loginUser = userService.getUserVO(request);
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (!order.getOrderUserId().equals(loginUser.getUserId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限删除订单");
        }
        return removeById(orderId);
    }

    @Override
    public Order getOrder(int orderId, HttpServletRequest request) {
        UserVO loginUser = userService.getUserVO(request);
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (!order.getOrderUserId().equals(loginUser.getUserId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限查看订单");
        }
        return order;
    }

    @Override
    public Page<Order> listOrdersByPage(Page<Order> page, QueryWrapper<Order> queryWrapper, HttpServletRequest request) {
        UserVO loginUser = userService.getUserVO(request);
        if (!userService.isAdmin(loginUser)) {
            queryWrapper.eq("orderUserId", loginUser.getUserId());
        }
        return page(page, queryWrapper);
    }

    @Override
    public void placeOrder(int orderId, HttpServletRequest request) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        UserVO loginUser = userService.getUserVO(request);
        if (!order.getOrderUserId().equals(loginUser.getUserId())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限下订单");
        }
        order.setOrderPayDate(DateTime.now());
        order.setOrderStatus(1); // 修改状态为1
        boolean result = updateById(order);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    @Override
    public List<CartItem> getCartItems(int cartId) {
        // 这里调用购物车服务获取购物车中的所有产品信息
        return cartService.getCartItems(cartId);
    }
}
