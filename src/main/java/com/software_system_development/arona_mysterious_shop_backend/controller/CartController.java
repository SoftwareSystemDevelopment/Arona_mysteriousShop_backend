package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Tag(name = "购物车接口")
public class CartController {

    @Resource
    CartService cartService;

    @GetMapping("/{cartId}")
    public Cart getCart(@PathVariable int cartId) {
        return cartService.getById(cartId);
    }

    @GetMapping("/{cartId}/items")
    public List<CartItem> getCartItems(@PathVariable int cartId) {
        return cartService.getCartItems(cartId);
    }
}
