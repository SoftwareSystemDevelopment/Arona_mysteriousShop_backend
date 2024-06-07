package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartItemMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private CartItemMapper cartItemMapper;

    @Override
    public boolean save(Cart cart) {
        int result = cartMapper.insert(cart);
        if (result > 0) {
            return true;
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "购物车创建失败");
        }
    }

    @Override
    public boolean update(Cart cart, List<CartItem> cartItems) {
        // 更新购物车中的商品信息
        cartItemMapper.delete(new QueryWrapper<CartItem>().eq("cart_id", cart.getCartId()));
        for (CartItem cartItem : cartItems) {
            cartItem.setCartId(cart.getCartId());
            cartItemMapper.insert(cartItem);
        }
        return true;
    }

    @Override
    public Cart getById(int id) {
        return cartMapper.selectById(id);
    }

    @Override
    public List<CartItem> getCartItems(int cartId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cart_id", cartId);
        return cartItemMapper.selectList(queryWrapper);
    }
}
