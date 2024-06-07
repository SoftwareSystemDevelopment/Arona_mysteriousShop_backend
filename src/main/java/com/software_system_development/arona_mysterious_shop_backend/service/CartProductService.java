package com.software_system_development.arona_mysterious_shop_backend.service;

/**
* @author 29967
* @description 针对表【cart_product】的数据库操作Service
* @createDate 2024-06-06 22:12:08
*/

import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;

import java.util.List;
/**
 * @author 29967
 * @description 针对表【cart_product】的数据库操作Service
 * @createDate 2024-06-06 22:12:08
 */

public interface CartProductService {
    List<CartProduct> getCartProductsByCartId(int cartId);
    boolean removeCartProductById(int cartProductId);
    boolean clearCartProducts(int cartId);
}

