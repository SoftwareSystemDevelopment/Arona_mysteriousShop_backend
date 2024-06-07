package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.annotation.AuthCheck;
import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.exception.ThrowUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductAddRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.product.ProductUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
     *  综合查询商品信息
     * @param productName
     * @param productCategoryName
     * @param minPrice
     * @param maxPrice
     * @param request
     * @return
     */
    @GetMapping("/search")
    @Operation(summary = "综合查询商品信息")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public List<Product> searchProduct(@RequestParam(required = false) String productName,
                                       @RequestParam(required = false) String productCategoryName,
                                       @RequestParam(required = false) BigDecimal minPrice,
                                       @RequestParam(required = false) BigDecimal maxPrice,
                                       @RequestParam(required = false) String description,
                                       HttpServletRequest request) {
        // 获取当前用户的Id
        UserVO currentUser = userService.getUserVO(request);
        Integer currentUserProviderId = currentUser.getUserId();

        // 执行查询操作
        List<Product> products = Collections.emptyList();
        if (productName != null) {
            products = productService.getByProductName(productName);
        } else if (productCategoryName != null) {
            products = productService.getByProductCategoryName(productCategoryName);
        } else if (minPrice != null) {
            if (maxPrice != null) {
                // 查询区间 [minPrice, maxPrice] 的商品
                products = productService.getByProductPriceRange(minPrice, maxPrice);
            } else {
                // 查询大于等于 minPrice 的商品
                products = productService.getByMinProductPrice(minPrice);
            }
        } else if (maxPrice != null) {
            // 查询小于等于 maxPrice 的商品
            products = productService.getByMaxProductPrice(maxPrice);
        } else if (description != null) {
            products = productService.getByDescription(description);
        }
        // 过滤掉不属于当前用户供应商的商品
        products = products.stream()
                .filter(product -> product.getProviderId().equals(currentUserProviderId))
                .collect(Collectors.toList());
        // 检查是否为空并返回结果
        ThrowUtils.throwIf(products.isEmpty(), ErrorCode.NOT_FOUND_ERROR);
        return products;
    }


    /**
     *  综合查询商品信息（VO）
     * @param productName
     * @param productCategoryName
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @GetMapping("/search/vo")
    @Operation(summary = "综合查询商品信息（VO）")
    public List<ProductVO> searchProductVO(@RequestParam(required = false) String productName,
                                           @RequestParam(required = false) String productCategoryName,
                                           @RequestParam(required = false) BigDecimal minPrice,
                                           @RequestParam(required = false) BigDecimal maxPrice,
                                           @RequestParam(required = false) String description) {
        // 执行查询操作
        List<Product> products;
        if (productName != null) {
            products = productService.getByProductName(productName);
        } else if (productCategoryName != null) {
            products = productService.getByProductCategoryName(productCategoryName);
        } else if (minPrice != null) {
            if (maxPrice != null) {
                // 查询闭区间 [minPrice, maxPrice] 的商品
                products = productService.getByProductPriceRange(minPrice, maxPrice);
            } else {
                // 查询大于等于 minPrice 的商品
                products = productService.getByMinProductPrice(minPrice);
            }
        } else if (maxPrice != null) {
            // 查询小于等于 maxPrice 的商品
            products = productService.getByMaxProductPrice(maxPrice);
        } else if (description != null) {
            products = productService.getByDescription(description);
        } else {
            products = productService.list();
        }
        // 转换为 ProductVO
        List<ProductVO> productVOList = productService.getProductVO(products);
        // 检查是否为空并返回结果
        ThrowUtils.throwIf(productVOList.isEmpty(), ErrorCode.NOT_FOUND_ERROR);
        return productVOList;
    }


    @GetMapping("/list/product")
    @Operation(summary = "获取供货商的产品列表（仅供货商）")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<List<Product>> listProductsByProvider(HttpServletRequest request) {
        // 获取当前用户的供货商ID
        UserVO loginUser = userService.getUserVO(request);
        int providerId = loginUser.getUserId();
        // 根据供货商ID查询产品列表
        List<Product> productList = productService.getByProviderId(providerId);
        return ResultUtils.success(productList);
    }

    @GetMapping("/list/vo")
    @Operation(summary = "获取产品VO列表")
    public BaseResponse<List<ProductVO>> listProductVO() {
        // 查询全部产品列表
        List<Product> productList = productService.list();
        // 将产品列表转换为产品封装列表
        List<ProductVO> productVOList = productService.getProductVO(productList);
        return ResultUtils.success(productVOList);
    }

    @GetMapping("/list/vo/provider/{providerId}")
    @Operation(summary = "获取供应商的产品VO列表")
    public BaseResponse<List<ProductVO>> listProductVOByProvider(@PathVariable Integer providerId) {
        // 根据供应商ID查询产品列表
        List<Product> productList = productService.getByProviderId(providerId);
        // 将产品列表转换为产品封装列表
        List<ProductVO> productVOList = productService.getProductVO(productList);
        return ResultUtils.success(productVOList);
    }

}

