package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;

import java.util.List;

public interface CartService {

    /**
     * 为用户创建购物车
     * @param cart 购物车对象
     * @return 创建的购物车的ID
     */
    boolean save(Cart cart);

    /**
     * 更新购物车
     * @param cart 购物车对象
     * @param cartItems 购物车商品列表
     * @return 更新后的购物车的ID
     */
    boolean update(Cart cart, List<CartItem> cartItems);

    /**
     * 根据ID获取购物车信息
     * @param id 购物车ID
     * @return 购物车对象
     */
    Cart getById(int id);

    /**
     * 获取购物车中的商品列表
     * @param cartId 购物车ID
     * @return 购物车中的商品列表
     */
    List<CartItem> getCartItems(int cartId);
}
