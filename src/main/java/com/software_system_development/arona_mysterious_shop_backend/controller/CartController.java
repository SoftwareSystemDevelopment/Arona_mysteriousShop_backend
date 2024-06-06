package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
//    @Autowired
//    private CartService cartService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/{userId}")
//    public Cart getCartByUserId(@PathVariable Long userId) {
//        return cartService.getCartByUserId(userId);
//    }
//
//    @PostMapping("/create")
//    public Cart createCart(@RequestParam Long userId) {
//        User user = userService.getUserById(userId);
//        return cartService.createCart(user);
//    }
//
//    @PostMapping("/{cartId}/add")
//    public CartItem addCartItem(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
//        return cartService.addCartItem(cartId, productId, quantity);
//    }
//
//    @DeleteMapping("/item/{cartItemId}")
//    public void removeCartItem(@PathVariable Long cartItemId) {
//        cartService.removeCartItem(cartItemId);
//    }
//
//    @GetMapping("/{cartId}/items")
//    public List<CartItem> getCartItems(@PathVariable Long cartId) {
//        return cartService.getCartItems(cartId);
//    }
}
