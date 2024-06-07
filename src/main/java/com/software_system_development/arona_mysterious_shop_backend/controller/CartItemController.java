package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart/item")
public class CartItemController {

    @Resource
    private CartItemService cartItemService;

    @GetMapping("/{cartId}")
    @Operation(summary = "Get all items in a specific cart")
    public BaseResponse<List<CartItem>> getCartItemsByCartId(@PathVariable int cartId) {
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        return ResultUtils.success(cartItems);
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(summary = "Remove item from cart by cart item id")
    public BaseResponse<Boolean> removeCartItemById(@PathVariable int cartItemId) {
        boolean result = cartItemService.removeCartItemById(cartItemId);
        return ResultUtils.success(result);
    }

    @DeleteMapping("/clear/{cartId}")
    @Operation(summary = "Clear all items from a specific cart")
    public BaseResponse<Boolean> clearCartItems(@PathVariable int cartId) {
        boolean result = cartItemService.clearCartItems(cartId);
        return ResultUtils.success(result);
    }
}
