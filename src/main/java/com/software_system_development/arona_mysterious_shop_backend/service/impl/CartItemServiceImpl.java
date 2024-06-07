package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software_system_development.arona_mysterious_shop_backend.service.CartItemService;
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
