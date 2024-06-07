package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;

/**
* @author 29967
* @description 针对表【cart】的数据库操作Service
* @createDate 2024-06-06 22:12:08
*/
import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    Cart createCart(Cart cart);
    Cart getCartByUserId(int userId);
    CartItem addCartItem(int userId, CartItem cartItem);
    boolean removeCartItem(int userId, int productId);
    boolean updateCartItemQuantity(int userId, int productId, int quantity);
    List<CartItem> getCartItems(int userId);
}
