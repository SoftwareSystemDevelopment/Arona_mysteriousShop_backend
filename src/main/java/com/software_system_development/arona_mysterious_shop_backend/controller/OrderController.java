package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.annotation.AuthCheck;
import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.*;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.OrderVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
@Tag(name = "订单接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private ProductService productService;

    @Resource
    private AddressService addressService;

    @Resource
    private UserService userService;

    @PostMapping("/add")
    @Operation(summary = "新增订单")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<Integer> addOrder(@RequestBody OrderAddRequest orderAddRequest, HttpServletRequest request) {
        if (orderAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = orderService.addOrder(orderAddRequest, request);
        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    @Operation(summary = "更新订单")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<Integer> updateOrder(@RequestBody OrderUpdateRequest orderUpdateRequest, HttpServletRequest request) {
        if (orderUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = orderService.updateOrder(orderUpdateRequest, request);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除订单")
    public BaseResponse<Boolean> deleteOrder(@RequestParam int orderId, HttpServletRequest request) {
        if (orderId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = orderService.removeOrder(orderId, request);
        return ResultUtils.success(result);
    }

    @GetMapping("/get")
    @Operation(summary = "获取订单详情")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<Order> getOrder(@RequestParam int orderId, HttpServletRequest request) {
        if (orderId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Order order = orderService.getOrder(orderId, request);
        return ResultUtils.success(order);
    }

    @PostMapping("/list")
    @Operation(summary = "分页获取订单列表")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<Page<Order>> listOrders(@RequestBody Page<Order> page, @RequestBody QueryWrapper<Order> queryWrapper, HttpServletRequest request) {
        Page<Order> orderPage = orderService.listOrdersByPage(page, queryWrapper, request);
        return ResultUtils.success(orderPage);
    }

    @PostMapping("/place")
    @Operation(summary = "下订单")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<OrderVO> placeOrder(@RequestParam int orderId, HttpServletRequest request) {
        if (orderId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 从HttpServletRequest中获取当前登录用户
        UserVO loginUser = userService.getUserVO(request);

        // 通过UserService获取用户对应的购物车ID
        Integer cartId = userService.getCartByUserId(loginUser.getUserId()).getCartId();

        // 下订单
        orderService.placeOrder(orderId, request);
        // 获取订单基本信息
        Order order = orderService.getOrder(orderId, request);
        String orderCode = order.getOrderCode();
        Integer orderAddressId = order.getOrderAddress();
        String orderReceiver = order.getOrderReceiver();
        String orderMobile = order.getOrderMobile();
        Date orderPayDate = order.getOrderPayDate();
        int orderStatus = order.getOrderStatus();
        // 根据地址ID获取地址信息
        Address address = addressService.getById(orderAddressId);
        String orderAddress = address.getAddressName();
        // 获取购物车商品列表
        List<CartItem> cartItems = orderService.getCartItems(cartId);
        // 构造订单商品信息列表
        List<OrderItemInfo> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            // 根据商品ID获取商品名称
            Product product = productService.getById(cartItem.getProductId());
            String productName = product.getProductName();
            OrderItemInfo orderItemInfo = new OrderItemInfo (
                    cartItem.getProductId(),
                    productName,
                    cartItem.getPrice(),
                    cartItem.getQuantity()
            );
            orderItems.add(orderItemInfo);
        }
        // 构造订单完整信息并返回给前端
        OrderVO orderVo = new OrderVO (
                orderCode,
                orderAddress,
                orderReceiver,
                orderMobile,
                orderPayDate,
                orderStatus,
                orderItems
        );
        return ResultUtils.success(orderVo);
    }
}
