package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartItemMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.ProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.UserMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.service.CartItemService;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 29967
* @description 针对表【cart_item】的数据库操作Service实现
* @createDate 2024-06-06 22:12:08
*/

@Service
public class CartItemServiceImpl implements CartItemService {

    @Resource
    private CartItemMapper cartItemMapper;

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        return cartItemMapper.selectList(new QueryWrapper<CartItem>().eq("cart_id", cartId));
    }

    @Override
    public boolean removeCartItemById(int cartItemId) {
        int deleted = cartItemMapper.deleteById(cartItemId);
        return deleted > 0;
    }

    @Override
    public boolean clearCartItems(int cartId) {
        int deleted = cartItemMapper.delete(new QueryWrapper<CartItem>().eq("cart_id", cartId));
        return deleted > 0;
    }
}
