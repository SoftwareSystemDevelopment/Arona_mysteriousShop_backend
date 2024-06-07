package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.service.CartProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 29967
* @description 针对表【cart_product】的数据库操作Service实现
* @createDate 2024-06-06 22:12:08
*/

@Service
public class CartProductServiceImpl implements CartProductService {

    @Resource
    private CartProductMapper cartProductMapper;

    @Override
    public List<CartProduct> getCartProductsByCartId(int cartId) {
        return cartProductMapper.selectList(new QueryWrapper<CartProduct>().eq("cartId", cartId));
    }

    @Override
    public boolean removeCartProductById(int cartProductId) {
        int deleted = cartProductMapper.deleteById(cartProductId);
        return deleted > 0;
    }

    @Override
    public boolean clearCartProducts(int cartId) {
        int deleted = cartProductMapper.delete(new QueryWrapper<CartProduct>().eq("cartId", cartId));
        return deleted > 0;
    }
}
