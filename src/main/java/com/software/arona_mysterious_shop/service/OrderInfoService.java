package com.software.arona_mysterious_shop.service;

import com.software.arona_mysterious_shop.model.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.arona_mysterious_shop.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author 29967
* @description 针对表【order_info(订单表)】的数据库操作Service
* @createDate 2024-06-04 21:20:18
*/
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     *  检查订单信息合法性
     * @param orderInfo
     * @param add
     */
    void validOrder(OrderInfo orderInfo, boolean add);

}
