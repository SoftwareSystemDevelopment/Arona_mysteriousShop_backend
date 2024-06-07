package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartItemMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 29967
* @description 针对表【cart】的数据库操作Service实现
* @createDate 2024-06-06 22:12:08
*/
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private CartItemMapper cartItemMapper;

    @Override
    public Cart createCart(Cart cart) {
        cartMapper.insert(cart);
        return cart;
    }

    @Override
    public Cart getCartByUserId(int userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return cartMapper.selectOne(queryWrapper);
    }

    @Override
    public CartItem addCartItem(int userId, CartItem cartItem) {
        Cart cart = getCartByUserId(userId);
        cartItem.setCartId(cart.getCartId());
        cartItemMapper.insert(cartItem);
        return cartItem;
    }

    @Override
    public boolean removeCartItem(int userId, int productId) {
        Cart cart = getCartByUserId(userId);
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cart_id", cart.getCartId()).eq("product_id", productId);
        int result = cartItemMapper.delete(queryWrapper);
        return result > 0;
    }

    @Override
    public boolean updateCartItemQuantity(int userId, int productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cart_id", cart.getCartId()).eq("product_id", productId);
        CartItem cartItem = cartItemMapper.selectOne(queryWrapper);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemMapper.updateById(cartItem);
            return true;
        }
        return false;
    }

    @Override
    public List<CartItem> getCartItems(int userId) {
        Cart cart = getCartByUserId(userId);
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cart_id", cart.getCartId());
        return cartItemMapper.selectList(queryWrapper);
    }
}





