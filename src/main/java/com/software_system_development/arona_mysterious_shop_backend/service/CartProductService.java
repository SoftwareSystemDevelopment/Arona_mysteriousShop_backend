package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.cartproduct.CartProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.cartproduct.CartProductRemoveRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 29967
 * @description 针对表【cart_product】的数据库操作Service
 * @createDate 2024-06-06 22:12:08
 */

public interface CartProductService extends IService<CartProduct> {
    /**
     * 添加商品到购物车
     * @param cartProductAddRequest
     * @param request
     * @return
     */
    boolean addCartProduct(CartProductAddRequest cartProductAddRequest, HttpServletRequest request);

    /**
     * 删除某个购物车项
     * @param cartProductRemoveRequest
     * @param request
     * @return
     */
    public boolean removeCartProductById(CartProductRemoveRequest cartProductRemoveRequest, HttpServletRequest request);

    /**
     * 清空某个购物车中的所有购物车项
     * @param cartId
     * @return
     */
    boolean clearCartProducts(int cartId);
}

