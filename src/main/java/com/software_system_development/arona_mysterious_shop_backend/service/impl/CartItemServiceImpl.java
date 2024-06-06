package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartItemMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.CartMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.ProductMapper;
import com.software_system_development.arona_mysterious_shop_backend.mapper.UserMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartItem;
import com.software_system_development.arona_mysterious_shop_backend.service.CartItemService;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【cart_item】的数据库操作Service实现
* @createDate 2024-06-06 22:12:08
*/
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem>
    implements CartItemService {
//    @Resource
//    private CartService cartService;
//    @Resource
//    private ProductService productService;
//    @Resource
//    private UserService userService;
//
//    public Cart getCartByUserId(Long userId) {
//        return cartService.findByUserUserId(userId).stream().findFirst().orElse(null);
//    }
//
//    public Cart createCart(User user) {
//        Cart cart = new Cart();
//        cart.setUser(user);
//        return cartService.save(cart);
//    }
//
//    public CartItem addCartItem(Long cartId, Long productId, int quantity) {
//        Optional<Cart> cartOptional = cartRepository.findById(cartId);
//        if (cartOptional.isPresent()) {
//            Cart cart = cartOptional.get();
//            Optional<Product> productOptional = productRepository.findById(productId);
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                CartItem cartItem = new CartItem();
//                cartItem.setCart(cart);
//                cartItem.setProduct(product);
//                cartItem.setQuantity(quantity);
//                cartItem.setPrice(product.getProductPrice().multiply(BigDecimal.valueOf(quantity)));
//                return cartItemRepository.save(cartItem);
//            }
//        }
//        return null;
//    }
//
//    public void removeCartItem(Long cartItemId) {
//        cartItemRepository.deleteById(cartItemId);
//    }
//
//    public List<CartItem> getCartItems(Long cartId) {
//        return cartItemRepository.findByCartCartId(cartId);
//    }
}




