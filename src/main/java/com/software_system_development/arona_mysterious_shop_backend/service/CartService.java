package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;

import java.util.List;

/**
 * @author 29967
 * @description 针对表【cart】的数据库操作Service
 * @createDate 2024-06-05 12:28:45
 */

public interface CartService extends IService<Cart> {

    /**
     * 为用户创建购物车
     * @param cart 购物车对象
     * @return 创建的购物车的ID
     */
    boolean save(Cart cart);

    /**
     * 更新购物车
     * @param cart 购物车对象
     * @param cartProducts 购物车商品列表
     * @return 更新后的购物车的ID
     */
    boolean update(Cart cart, List<CartProduct> cartProducts);

    /**
     * 获取购物车中的商品列表
     * @param cartId 购物车ID
     * @return 购物车中的商品列表
     */
    List<CartProduct> getCartProducts(int cartId);
}
