package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.ProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.CartProductVO;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private ProductMapper productMapper;

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
    public List<CartProduct> getCartProducts(int cartId) {
        QueryWrapper<CartProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cartId", cartId);
        return cartProductMapper.selectList(queryWrapper);
    }

    @Override
    public List<CartProductVO> getCartProductVOs(int cartId) {
        List<CartProduct> cartProducts = getCartProducts(cartId);
        return cartProducts.stream().map(cartProduct -> {
            CartProductVO cartProductVO = new CartProductVO();
            BeanUtils.copyProperties(cartProduct, cartProductVO);
            cartProductVO.setProductName(productMapper.selectById(cartProduct.getProductId()).getProductName());
            cartProductVO.setProductPrice(productMapper.selectById(cartProduct.getProductId()).getProductPrice());
            return cartProductVO;
        }).toList();
    }
}
