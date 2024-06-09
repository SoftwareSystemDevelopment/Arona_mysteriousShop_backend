package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.OrderItemMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.OrderMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Order;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.OrderItem;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.OrderVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    UserService userService;

    @Resource
    AddressService addressService;

    @Resource
    CartService cartService;

    @Resource
    CartProductService cartProductService;

    @Resource
    ProductService productService;

    @Resource
    OrderItemMapper orderItemMapper;



    @Transactional
    @Override
    public int addOrder(OrderAddRequest orderAddRequest, HttpServletRequest request) {
        // 获取当前登录用户
        UserVO loginUser = userService.getUserVO(request);
        // 校验
        if (orderAddRequest.getOrderCode() == null || orderAddRequest.getOrderAddress() == null ||
                orderAddRequest.getOrderReceiver() == null || orderAddRequest.getOrderMobile() == null ||
                orderAddRequest.getOrderUserId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 检查订单编号是否重复
        if (isOrderCodeExists(orderAddRequest.getOrderCode())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "订单编号已存在");
        }
        // 校验是否有权限下订单
        if (!orderAddRequest.getOrderUserId().equals(loginUser.getUserId())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限下订单");
        }
        // 检查收货地址是否合法
        addressService.getAddressById(orderAddRequest.getOrderAddress());

        // 获取购物车中的商品项
        Integer cartId = userService.getCartId(loginUser);
        List<CartProduct> cartProducts = cartService.getCartProducts(cartId);
        if (cartProducts.isEmpty()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "购物车为空，无法下订单");
        }

        // 创建订单对象并保存
        Order order = new Order();
        BeanUtils.copyProperties(orderAddRequest, order);
        order.setOrderStatus(1); // 订单状态设置为已下单
        order.setOrderPayDate(DateTime.now());
        order.setOrderUserId(loginUser.getUserId());
        boolean orderResult = save(order);
        if (!orderResult) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "订单保存失败");
        }
        // 创建订单项并保存
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartProduct cartProduct : cartProducts) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setProductId(cartProduct.getProductId());
            orderItem.setProductName(productService.getProductName(cartProduct.getProductId()));
            orderItem.setQuantity(cartProduct.getQuantity());
            orderItem.setPrice(productService.getProductPrice(cartProduct.getProductId()));
            orderItems.add(orderItem);
        }
        boolean orderItemsResult = orderItemMapper.insertBatch(orderItems);
        if (!orderItemsResult) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "订单项保存失败");
        }
        // 清空购物车
        cartProductService.clearCartProducts(cartId);
        return order.getOrderId();
    }

    /**
     * 检查订单编号是否已存在
     */
    private boolean isOrderCodeExists(String orderCode) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderCode", orderCode);
        return count(queryWrapper) > 0;
    }


    @Override
    public int updateOrder(OrderUpdateRequest orderUpdateRequest, HttpServletRequest request) {
        UserVO loginUser = userService.getUserVO(request);
        Order order = getById(orderUpdateRequest.getOrderId());
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "订单不存在");
        }
        if (!order.getOrderUserId().equals(loginUser.getUserId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限修改订单");
        }
        //检查修改后地址是否合法
        addressService.getAddressById(orderUpdateRequest.getOrderAddress());

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
    public OrderVO getOrder(int orderId, HttpServletRequest request) {
        UserVO loginUser = userService.getUserVO(request);
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (!order.getOrderUserId().equals(loginUser.getUserId())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限查看订单");
        }
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        orderVO.setOrderAddress(addressService.getAddressById(order.getOrderAddress()).getAddressName());
        List<OrderItem> orderItems = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("orderId", orderId));
        orderVO.setOrderItems(orderItems);
        return orderVO;
    }

    @Override
    public QueryWrapper<Order> getQueryWrapper(String orderCode, String receiverName, String orderStatus) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // 添加订单号查询条件
        if (!StringUtils.isEmpty(orderCode)) {
            queryWrapper.like("orderCode", orderCode);
        }
        // 添加收货人查询条件
        if (!StringUtils.isEmpty(receiverName)) {
            queryWrapper.like("receiverName", receiverName);
        }
        // 添加订单状态查询条件
        if (!StringUtils.isEmpty(orderStatus)) {
            queryWrapper.eq("orderStatus", orderStatus);
        }
        return queryWrapper;
    }

    @Override
    public List<OrderVO> getOrderVO(List<Order> orderList) {
        return orderList.stream().map(order -> {
            OrderVO orderVO = new OrderVO();
            orderVO.setOrderCode(order.getOrderCode());
            orderVO.setOrderAddress(addressService.getAddressById(order.getOrderAddress()).getAddressName());
            orderVO.setOrderReceiver(order.getOrderReceiver());
            orderVO.setOrderMobile(order.getOrderMobile());
            orderVO.setOrderPayDate(order.getOrderPayDate());
            orderVO.setOrderStatus(order.getOrderStatus());
            orderVO.setOrderItems(orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("orderId", order.getOrderId())));
            return orderVO;
        }).collect(Collectors.toList());
    }

}
