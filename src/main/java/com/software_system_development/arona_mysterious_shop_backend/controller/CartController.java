package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart")
@Tag(name = "购物车接口")
public class CartController {

    @Resource
    private CartService cartService;

    @PostMapping("/user/add-item")
    @Operation(summary = "向购物车中添加商品")
    public BaseResponse<CartItem> addCartItem(@RequestBody CartItem cartItem, HttpServletRequest request) {
        CartItem addedCartItem = cartService.addCartItem(request, cartItem);
        return ResultUtils.success(addedCartItem);
    }

    @DeleteMapping("/user/remove-item/{productId}")
    @Operation(summary = "从购物车中移除商品")
    public BaseResponse<Boolean> removeCartItem(@PathVariable int productId, HttpServletRequest request) {
        boolean result = cartService.removeCartItem(request, productId);
        return ResultUtils.success(result);
    }

    @PutMapping("/user/update-item")
    @Operation(summary = "更新商品数量")
    public BaseResponse<Boolean> updateCartItemQuantity(@RequestBody CartItem cartItem, HttpServletRequest request) {
        boolean result = cartService.updateCartItemQuantity(request, cartItem.getProductId(), cartItem.getQuantity());
        return ResultUtils.success(result);
    }

    @GetMapping("/user/items")
    @Operation(summary = "获取购物车中所有商品信息")
    public BaseResponse<List<CartItem>> getCartItems(HttpServletRequest request) {
        List<CartItem> cartItems = cartService.getCartItems(request);
        return ResultUtils.success(cartItems);
    }
}
