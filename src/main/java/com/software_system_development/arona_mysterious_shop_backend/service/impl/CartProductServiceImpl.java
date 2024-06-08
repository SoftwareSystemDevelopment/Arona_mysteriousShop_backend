package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.ProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.cartproduct.CartProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.cartproduct.CartProductRemoveRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.CartProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


/**
 * @author 29967
 * @description 针对表【cartproduct】的数据库操作Service实现
 * @createDate 2024-06-08 12:28:45
 */

@Service
public class CartProductServiceImpl extends ServiceImpl<CartProductMapper, CartProduct> implements CartProductService {

    @Resource
    private CartProductMapper cartProductMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private UserService userService;

    @Override
    public boolean removeCartProductById(CartProductRemoveRequest cartProductRemoveRequest, HttpServletRequest request) {
        // 获取当前购物车ID
        UserVO userVO = userService.getUserVO(request);
        int cartId = userVO.getCartId();

        // 查询购物车中是否存在该商品
        int productId = cartProductRemoveRequest.getProductId();
        QueryWrapper<CartProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productId", productId).eq("cartId", cartId);
        CartProduct cartProduct = cartProductMapper.selectOne(queryWrapper);

        int deleteQuantity = cartProductRemoveRequest.getQuantity();
        int currentQuantity = cartProduct.getQuantity();
        if (deleteQuantity > currentQuantity) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "移除数量大于购物车中的数量");
        } else if (deleteQuantity == currentQuantity) {
            // 如果移除数量等于当前购物车中的数量，则直接删除该商品
            return cartProductMapper.deleteById(cartProduct) > 0;
        } else {
            // 更新商品数量
            cartProduct.setQuantity(currentQuantity - deleteQuantity);
            return cartProductMapper.updateById(cartProduct) > 0;
        }
    }

    @Override
    public boolean clearCartProducts(int cartId) {
        // 清空购物车商品项
        int deleted = cartProductMapper.delete(new QueryWrapper<CartProduct>().eq("cartId", cartId));
        return deleted > 0;
    }

    @Override
    public boolean addCartProduct(CartProductAddRequest cartProductAddRequest, HttpServletRequest request) {
        // 对参数进行校验
        if (cartProductAddRequest.getProductId() == null || cartProductAddRequest.getQuantity() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        int quantity = cartProductAddRequest.getQuantity();
        int productId = cartProductAddRequest.getProductId();
        if (quantity <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "数量需要大于0");
        }
        // 查找商品是否存在
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品不存在");
        }
        // 获取到当前购物车ID
        UserVO userVO = userService.getUserVO(request);
        int cartId = userVO.getCartId();

        // 查询购物车中是否已经存在该商品
        CartProduct existingCartProduct = cartProductMapper.selectOne(new QueryWrapper<CartProduct>()
                .eq("cartId", cartId)
                .eq("productId", productId));

        if (existingCartProduct != null) {
            // 商品已经存在于购物车中，更新商品数量
            existingCartProduct.setQuantity(existingCartProduct.getQuantity() + quantity);
            int updated = cartProductMapper.updateById(existingCartProduct);
            if (updated > 0) {
                return true;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新购物车商品数量失败");
            }
        } else {
            // 商品不存在于购物车中，新增商品项到购物车
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProductId(productId);
            cartProduct.setQuantity(quantity);
            cartProduct.setCartId(cartId);
            // 插入商品项到购物车
            int inserted = cartProductMapper.insert(cartProduct);
            return inserted > 0;
        }
    }
}
