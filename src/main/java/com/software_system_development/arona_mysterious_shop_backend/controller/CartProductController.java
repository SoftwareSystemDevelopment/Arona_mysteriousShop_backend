package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.cartproduct.CartProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.cartproduct.CartProductRemoveRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.CartProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/cart/item")
@Tag(name = "购物车项接口")
public class CartProductController {

    @Resource
    private CartProductService cartProductService;


    @PostMapping
    @Operation(summary = "添加商品指定数量到购物车")
    public BaseResponse<Boolean> addCartProduct(@RequestBody CartProductAddRequest cartProductAddRequest, HttpServletRequest request) {
        boolean result = cartProductService.addCartProduct(cartProductAddRequest, request);
        return ResultUtils.success(result);
    }

    @DeleteMapping("/remove")
    @Operation(summary = "从购物车中删除商品指定数量")
    public BaseResponse<Boolean> removeCartProductById(@RequestBody CartProductRemoveRequest cartProductRemoveRequest, HttpServletRequest request) {
        boolean result = cartProductService.removeCartProductById(cartProductRemoveRequest, request);
        return ResultUtils.success(result);
    }


    @DeleteMapping("/clear")
    @Operation(summary = "清空购物车中的所有商品")
    public BaseResponse<Boolean> clearCartProducts(HttpServletRequest request) {
        UserVO currentUser = (UserVO) request.getSession().getAttribute(USER_LOGIN_STATE);
        int cartId = currentUser.getCartId();
        boolean result = cartProductService.clearCartProducts(cartId);
        return ResultUtils.success(result);
    }
}
