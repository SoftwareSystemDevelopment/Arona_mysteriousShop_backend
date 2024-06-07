package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart/item")
@Tag(name = "购物车项接口")
public class CartItemController {

    @Resource
    private CartItemService cartItemService;

    @GetMapping("/{cartId}")
    @Operation(summary = "获取某个购物车中所有购物车项信息")
    public BaseResponse<List<CartItem>> getCartItemsByCartId(@PathVariable int cartId) {
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        return ResultUtils.success(cartItems);
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(summary = "删除某个购物车项")
    public BaseResponse<Boolean> removeCartItemById(@PathVariable int cartItemId) {
        boolean result = cartItemService.removeCartItemById(cartItemId);
        return ResultUtils.success(result);
    }

    @DeleteMapping("/clear/{cartId}")
    @Operation(summary = "清空某个购物车中的所有购物车项")
    public BaseResponse<Boolean> clearCartItems(@PathVariable int cartId) {
        boolean result = cartItemService.clearCartItems(cartId);
        return ResultUtils.success(result);
    }
}
