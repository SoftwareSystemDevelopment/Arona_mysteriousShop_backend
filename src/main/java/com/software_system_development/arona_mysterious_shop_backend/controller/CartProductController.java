package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.service.CartProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart/item")
@Tag(name = "购物车项接口")
public class CartProductController {

    @Resource
    private CartProductService cartProductService;

    @GetMapping("/{cartId}")
    @Operation(summary = "获取某个购物车中所有购物车项信息")
    public BaseResponse<List<CartProduct>> getCartProductsByCartId(@PathVariable int cartId) {
        List<CartProduct> cartProducts = cartProductService.getCartProductsByCartId(cartId);
        return ResultUtils.success(cartProducts);
    }

    @DeleteMapping("/{cartProductId}")
    @Operation(summary = "删除某个购物车项")
    public BaseResponse<Boolean> removeCartProductById(@PathVariable int cartProductId) {
        boolean result = cartProductService.removeCartProductById(cartProductId);
        return ResultUtils.success(result);
    }

    @DeleteMapping("/clear/{cartId}")
    @Operation(summary = "清空某个购物车中的所有购物车项")
    public BaseResponse<Boolean> clearCartProducts(@PathVariable int cartId) {
        boolean result = cartProductService.clearCartProducts(cartId);
        return ResultUtils.success(result);
    }
}
