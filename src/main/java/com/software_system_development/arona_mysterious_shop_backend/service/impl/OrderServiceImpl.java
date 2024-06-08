package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.OrderItemMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.OrderMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Address;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Order;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.OrderItem;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.OrderVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ProductVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
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
        UserVO loginUser = userService.getUserVO(request);

        if (!orderAddRequest.getOrderUserId().equals(loginUser.getUserId())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限下订单");
        }
        if(orderAddRequest.getOrderAddress() == null ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请填写收货地址");
        }

        //根据地址ID查询地址信息
        addressService.getAddressById(orderAddRequest.getOrderAddress());

        // 获取用户的购物车ID
        Integer cartId = userService.getCartId(loginUser);

        // 获取购物车中的商品项
        List<CartProduct> cartProducts = cartService.getCartProducts(cartId);
        if (cartProducts.isEmpty()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "购物车为空，无法下订单");
        }

        Order order = new Order();
        BeanUtils.copyProperties(orderAddRequest, order);
        order.setOrderStatus(1); // 订单状态设置为已下单
        order.setOrderPayDate(DateTime.now());
        order.setOrderUserId(loginUser.getUserId());

        boolean orderResult = save(order);
        if (!orderResult) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
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

        //清空购物车
        cartProductService.clearCartProducts(cartId);

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
        if (!order.getOrderUserId().equals(loginUser.getUserId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限查看订单");
        }
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderCode(order.getOrderCode());
        orderVO.setOrderAddress(addressService.getAddressById(order.getOrderAddress()).getAddressName());
        orderVO.setOrderReceiver(order.getOrderReceiver());
        orderVO.setOrderMobile(order.getOrderMobile());
        orderVO.setOrderPayDate(order.getOrderPayDate());
        orderVO.setOrderStatus(order.getOrderStatus());
        orderVO.setOrderItems(orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("orderId", orderId)));
        return orderVO;
    }

    @Override
    public List<Order> list(HttpServletRequest request) {
        // 获取当前登录用户信息
        UserVO loginUser = userService.getUserVO(request);
        // 构建查询条件
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (!userService.isAdmin(loginUser)) {
            queryWrapper.eq("orderUserId", loginUser.getUserId());
        }
        return baseMapper.selectList(queryWrapper);
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
