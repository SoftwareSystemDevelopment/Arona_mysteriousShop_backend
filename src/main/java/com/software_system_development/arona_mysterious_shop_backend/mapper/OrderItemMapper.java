package com.software_system_development.arona_mysterious_shop_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.OrderItem;

import java.util.List;

/**
* @author 29967
* @description 针对表【orderitem(订单项表)】的数据库操作Mapper
* @createDate 2024-06-08 11:09:22
* @Entity generator.domain.Orderitem
*/
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    boolean insertBatch(List<OrderItem> orderItems);
}




