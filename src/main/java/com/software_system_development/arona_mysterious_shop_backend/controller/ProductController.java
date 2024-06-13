package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.annotation.AuthCheck;
import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.PageVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ProductVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Resource
    private UserService userService;

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
     * @param productId 商品ID
     * @param request HttpServletRequest
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @DeleteMapping("/delete/{productId}")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    @Operation(summary = "删除商品")
    public BaseResponse<Boolean> deleteProduct(@PathVariable Integer productId, HttpServletRequest request) {
        if (productId == null || productId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = productService.deleteProduct(productId, request);
        return ResultUtils.success(result);
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
     * 分页获取供货商的产品列表（仅供货商）
     *
     * @param productName        商品名称
     * @param productCategory    商品所属分类
     * @param minPrice           最低价格
     * @param maxPrice           最高价格
     * @param productDescription 商品描述
     * @param current            当前页数
     * @param size               每页大小
     * @return {@link BaseResponse}<{@link Page}<{@link Product}>>
     */
    @GetMapping("/list/product")
    @Operation(summary = "供货商分页获取自己的产品列表")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<PageVO<Product>> listProductsByProvider(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productCategory,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String productDescription,
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size,
            HttpServletRequest request) {
        UserVO currentUser = userService.getUserVO(request);
        QueryWrapper<Product> queryWrapper = productService.getQueryWrapper(
                productName, productCategory, minPrice, maxPrice, productDescription);
        // 添加供应商ID查询条件
        queryWrapper.eq("providerId", currentUser.getUserId());
        // 执行分页查询
        Page<Product> productPage = productService.page(new Page<>(current, size), queryWrapper);
        PageVO<Product> productPageVO = new PageVO<>(productPage.getRecords(), productPage.getTotal());
        return ResultUtils.success(productPageVO);
    }

    /**
     * 分页获取产品封装列表
     *
     * @param productName        商品名称
     * @param productCategory    商品所属分类
     * @param minPrice           最低价格
     * @param maxPrice           最高价格
     * @param productDescription 商品描述
     * @param current            当前页数
     * @param size               每页大小
     * @return {@link BaseResponse}<{@link PageVO}<{@link ProductVO}>>
     */
    @GetMapping("/list/vo")
    @Operation(summary = "分页获取产品VO列表")
    public BaseResponse<PageVO<ProductVO>> listProductVOByPage(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productCategory,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String productDescription,
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        QueryWrapper<Product> queryWrapper = productService.getQueryWrapper(
                productName, productCategory, minPrice, maxPrice, productDescription);
        // 执行分页查询
        IPage<Product> productPage = productService.page(new Page<>(current, size), queryWrapper);
        List<ProductVO> productVOList = productService.getProductVO(productPage.getRecords());
        Page<ProductVO> productVOPage = new Page<>(current, size, productPage.getTotal());
        productVOPage.setRecords(productVOList);
        PageVO<ProductVO> productVOPageVO = new PageVO<>(productVOPage.getRecords(), productVOPage.getTotal());
        return ResultUtils.success(productVOPageVO);
    }

}

