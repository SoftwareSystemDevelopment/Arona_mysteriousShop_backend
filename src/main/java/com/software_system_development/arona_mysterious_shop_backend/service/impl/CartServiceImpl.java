package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 29967
 * @description 针对表【cart】的数据库操作Service实现
 * @createDate 2024-06-05 12:28:45
 */

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private CartProductMapper cartProductMapper;

    @Override
    public boolean save(Cart cart) {
        int result = cartMapper.insert(cart);
        if (result > 0) {
            return true;
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "购物车创建失败");
        }
    }

    @Override
    public boolean update(Cart cart, List<CartProduct> cartProducts) {
        // 更新购物车中的商品信息
        cartProductMapper.delete(new QueryWrapper<CartProduct>().eq("cartId", cart.getCartId()));
        for (CartProduct cartProduct : cartProducts) {
            cartProduct.setCartId(cart.getCartId());
            cartProductMapper.insert(cartProduct);
        }
        return true;
    }

    @Override
    public List<CartProduct> getCartProducts(int cartId) {
        QueryWrapper<CartProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cartId", cartId);
        return cartProductMapper.selectList(queryWrapper);
    }
}
