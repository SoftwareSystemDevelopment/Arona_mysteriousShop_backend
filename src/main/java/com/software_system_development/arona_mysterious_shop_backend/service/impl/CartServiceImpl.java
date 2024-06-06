package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【cart】的数据库操作Service实现
* @createDate 2024-06-06 22:12:08
*/
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart>
    implements CartService {

}




