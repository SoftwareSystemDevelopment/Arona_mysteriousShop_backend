package com.software_system_development.arona_mysterious_shop_backend.service;

import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 29967
* @description 针对表【cart】的数据库操作Service
* @createDate 2024-06-06 22:12:08
*/
import java.util.List;

public interface CartService {
    Cart createCart(int userId);
    Cart getCartByUserId(HttpServletRequest request);
    CartItem addCartItem(HttpServletRequest request, CartItem cartItem);
    boolean removeCartItem(HttpServletRequest request, int productId);
    boolean updateCartItemQuantity(HttpServletRequest request, int productId, int quantity);
    List<CartItem> getCartItems(HttpServletRequest request);
}
