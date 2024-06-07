package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/cart")
@Tag(name = "购物车接口")
public class CartController {

    @Resource
    CartService cartService;

    @GetMapping("")
    @Operation(summary = "获取当前用户的购物车信息")
    public Cart getCart(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserVO currentUser = (UserVO) userObj;
        int cartId = currentUser.getCartId();
        return cartService.getById(cartId);
    }

    @GetMapping("/items")
    @Operation(summary = "获取当前用户的购物车中的所有购物车项信息")
    public List<CartProduct> getCartProducts(@PathVariable int cartId) {
        return cartService.getCartProducts(cartId);
    }
}
