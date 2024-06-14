package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.order.OrderUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Order;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.OrderVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderService extends IService<Order> {

    /**
     * 下订单
     * @param orderAddRequest
     * @param request
     * @return
     */
    int addOrder(OrderAddRequest orderAddRequest, HttpServletRequest request);

    /**
     * 更新订单
     * @param orderUpdateRequest
     * @param request
     * @return
     */
    int updateOrder(OrderUpdateRequest orderUpdateRequest, HttpServletRequest request);

    /**
     * 删除订单
     * @param orderId
     * @param request
     * @return
     */
    boolean removeOrder(int orderId, HttpServletRequest request);

    /**
     * 获取订单详情
     * @param orderId
     * @param request
     * @return
     */
    OrderVO getOrder(int orderId, HttpServletRequest request);

    /**
     * 分页查询订单
     * @param orderId
     * @param receiverName
     * @param orderStatus
     * @return
     */
    QueryWrapper<Order> getQueryWrapper(Integer orderId, String receiverName, String orderStatus);

    /**
     * 获取orderVO
     * @param orderList
     * @return
     */
    List<OrderVO> getOrderVO(List<Order> orderList);
}
