package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.OrderItem;
import com.software_system_development.arona_mysterious_shop_backend.service.OrderitemService;
import com.software_system_development.arona_mysterious_shop_backend.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【orderitem(订单项表)】的数据库操作Service实现
* @createDate 2024-06-08 11:09:22
*/
@Service
public class OrderitemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderitemService{

}




