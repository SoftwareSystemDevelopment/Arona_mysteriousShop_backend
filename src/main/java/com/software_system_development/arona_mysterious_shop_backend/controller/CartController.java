package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @PostMapping("/user/{userId}/add-item")
    @Operation(summary = "Add item to cart")
    public BaseResponse<CartItem> addCartItem(@PathVariable int userId, @RequestBody CartItem cartItem) {
        CartItem addedCartItem = cartService.addCartItem(userId, cartItem);
        return ResultUtils.success(addedCartItem);
    }

    @DeleteMapping("/user/{userId}/remove-item/{productId}")
    @Operation(summary = "Remove item from cart")
    public BaseResponse<Boolean> removeCartItem(@PathVariable int userId, @PathVariable int productId) {
        boolean result = cartService.removeCartItem(userId, productId);
        return ResultUtils.success(result);
    }

    @PutMapping("/user/{userId}/update-item")
    @Operation(summary = "Update item quantity in cart")
    public BaseResponse<Boolean> updateCartItemQuantity(@PathVariable int userId, @RequestBody CartItem cartItem) {
        boolean result = cartService.updateCartItemQuantity(userId, cartItem.getProductId(), cartItem.getQuantity());
        return ResultUtils.success(result);
    }

    @GetMapping("/user/{userId}/items")
    @Operation(summary = "Get all items in cart")
    public BaseResponse<List<CartItem>> getCartItems(@PathVariable int userId) {
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResultUtils.success(cartItems);
    }
}
