package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.annotation.AuthCheck;
import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.exception.ThrowUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductDeleteRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductQueryRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.*;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ProductVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import com.software_system_development.arona_mysterious_shop_backend.utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/product")
@Slf4j
@Tag(name = "商品接口")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 增加商品
     *
     * @param productAddRequest
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @PostMapping("/add")
    @Operation(summary = "增加商品")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<Integer> addProduct(@RequestBody ProductAddRequest productAddRequest, HttpServletRequest request) {
        if (productAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = productService.addProduct(productAddRequest, request);
        return ResultUtils.success(result);
    }

    /**
     * 修改商品信息
     *
     */
    @PostMapping("/update")
    @Operation(summary = "修改商品信息")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<Integer> updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest, HttpServletRequest request) {
        if (productUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = productService.updateProduct(productUpdateRequest, request);
        return ResultUtils.success(result);
    }

    /**
     * 删除商品
     *
     * @param deleteRequest 删除请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    @Operation(summary = "删除商品")
    public BaseResponse<Boolean> deleteProduct(@RequestBody ProductDeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getProductId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = productService.removeProduct(deleteRequest, request);
        return ResultUtils.success(b);
    }


    /**
     * 根据 id  获取商品类
     *
     * @param id 编号
     * @return {@link BaseResponse}<{@link Product}>
     */
    @GetMapping("/get")
    @Operation(summary = "获取商品信息by id")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<Product> getProductById(int id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Product product = productService.getProduct(id, request);
        return ResultUtils.success(product);
    }

    /**
     * 根据id获取商品封装类
     * @param id
     */
    @GetMapping("/get/vo")
    @Operation(summary = "获取商品VOby id")
    public BaseResponse<ProductVO> getProductVOById(int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProductVO productVO = productService.getProductVO(id);
        return ResultUtils.success(productVO);
    }

    /**
     * 分页获取供应商商品列表
     *
     * @return {@link BaseResponse}<{@link Page}<{@link Product}>>
     */
    @GetMapping("/list/page")
    @Operation(summary = "分页获取供应商商品列表")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<Page<Product>> listProductByPage(@RequestParam(value = "current", defaultValue = "1") long current,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") long size) {
        // 获取当前登录的供应商ID
        Long providerId = ThreadLocalUtil.getLoginUser().getUserId();
        if (providerId == null || providerId <= 0) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无法获取当前用户信息");
        }
        // 构建供应商商品查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("providerId", providerId);
        Page<Product> productPage = productService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(productPage);
    }


    /**
     * 根据商品名称模糊搜索商品
     * @param productName
     * @return
     */
    @GetMapping("/search")
    @Operation(summary = "模糊搜索")
    public ResponseEntity<Page<ProductVO>> listProductByPage(@RequestParam String productName,
                                                             @RequestParam(defaultValue = "1") long current,
                                                             @RequestParam(defaultValue = "10") long size) {
        ProductQueryRequest productQueryRequest = new ProductQueryRequest();
        productQueryRequest.setCurrent(current);
        productQueryRequest.setPageSize(size);
        productQueryRequest.setProductName(productName);

        Page<ProductVO> productPage = productService.listProductsByPage(productQueryRequest);
        return ResponseEntity.ok(productPage);
    }
}

