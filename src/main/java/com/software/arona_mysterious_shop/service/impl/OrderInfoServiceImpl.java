package com.software.arona_mysterious_shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.arona_mysterious_shop.common.ErrorCode;
import com.software.arona_mysterious_shop.exception.BusinessException;
import com.software.arona_mysterious_shop.exception.ThrowUtils;
import com.software.arona_mysterious_shop.model.entity.OrderInfo;
import com.software.arona_mysterious_shop.model.entity.User;
import com.software.arona_mysterious_shop.model.enums.OrderStatusEnum;
import com.software.arona_mysterious_shop.service.OrderInfoService;
import com.software.arona_mysterious_shop.mapper.OrderInfoMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author 29967
* @description 针对表【order_info(订单表)】的数据库操作Service实现
* @createDate 2024-06-04 21:20:18
*/
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
    implements OrderInfoService{

    @Override
    public void validOrder(OrderInfo orderInfo, boolean add) {
        if (orderInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer status = orderInfo.getStatus();

        //订单状态不在枚举中
        ThrowUtils.throwIf(OrderStatusEnum.getEnumByValue(status) == null, ErrorCode.PARAMS_ERROR,"状态错误");
    }

}




