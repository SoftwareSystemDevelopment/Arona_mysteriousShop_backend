package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.CartProduct;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.CartProductVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.PageVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/cart")
@Tag(name = "购物车接口")
public class CartController {

    @Resource
    CartService cartService;

//    @GetMapping("")
//    @Operation(summary = "获取当前用户的购物车信息")
//    public Cart getCart(HttpServletRequest request) {
//        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        UserVO currentUser = (UserVO) userObj;
//        int cartId = currentUser.getCartId();
//        return cartService.getById(cartId);
//    }

    @GetMapping("/items")
    @Operation(summary = "获取当前用户的购物车中的所有购物车项信息")
    public BaseResponse<PageVO<CartProductVO>> getCartProducts(@RequestParam(defaultValue = "1") long current,
                                                               @RequestParam(defaultValue = "10") long size, HttpServletRequest request) {
        UserVO currentUser= (UserVO)request.getSession().getAttribute(USER_LOGIN_STATE);
        int cartId = currentUser.getCartId();
        List<CartProductVO> cartProductVOS = cartService.getCartProductVOs(cartId);
        IPage<CartProductVO> page = new Page<>(current, size);
        page.setRecords(cartProductVOS);
        PageVO<CartProductVO> pageVO = new PageVO<>(page.getRecords(),page.getTotal());
        return ResultUtils.success(pageVO);
    }
}
