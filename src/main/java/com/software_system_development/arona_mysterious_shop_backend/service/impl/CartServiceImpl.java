package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @Resource
    private UserService userService;

    @Override
    public Cart createCart(int userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cartMapper.insert(cart);
        return cart;
    }

    @Override
    public Cart getCartByUserId(HttpServletRequest request) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userService.getUserVO(request).getUserId());
        return cartMapper.selectOne(queryWrapper);
    }

    @Override
    public CartItem addCartItem(HttpServletRequest request, CartItem cartItem) {
        Cart cart = getCartByUserId(request);
        cartItem.setCartId(cart.getCartId());
        cartItemMapper.insert(cartItem);
        return cartItem;
    }

    @Override
    public boolean removeCartItem(HttpServletRequest request, int productId) {
        Cart cart = getCartByUserId(request);
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cartId", cart.getCartId()).eq("productId", productId);
        int result = cartItemMapper.delete(queryWrapper);
        return result > 0;
    }

    @Override
    public boolean updateCartItemQuantity(HttpServletRequest request, int productId, int quantity) {
        Cart cart = getCartByUserId(request);
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cartId", cart.getCartId()).eq("productId", productId);
        CartItem cartItem = cartItemMapper.selectOne(queryWrapper);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemMapper.updateById(cartItem);
            return true;
        }
        return false;
    }

    @Override
    public List<CartItem> getCartItems(HttpServletRequest request) {
        Cart cart = getCartByUserId(request);
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cart_id", cart.getCartId());
        return cartItemMapper.selectList(queryWrapper);
    }
}





