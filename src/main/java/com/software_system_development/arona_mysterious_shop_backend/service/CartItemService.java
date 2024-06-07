package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;

/**
* @author 29967
* @description 针对表【cart_item】的数据库操作Service
* @createDate 2024-06-06 22:12:08
*/
import java.util.List;
/**
 * @author 29967
 * @description 针对表【cart_item】的数据库操作Service
 * @createDate 2024-06-06 22:12:08
 */

public interface CartItemService {
    List<CartItem> getCartItemsByCartId(int cartId);
    boolean removeCartItemById(int cartItemId);
    boolean clearCartItems(int cartId);
}

