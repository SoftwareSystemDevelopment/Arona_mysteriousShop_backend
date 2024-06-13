package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.shop.ShopUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.PageVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ProductVO;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/shop")
@Slf4j
@Tag(name = "店铺接口")
public class ShopController {

    @Resource
    private ShopService shopService;

    @Resource
    private ProductService productService;

    @PostMapping("/update")
    @Operation(summary = "更新店铺信息")
    public BaseResponse<Boolean> updateShop(@RequestBody ShopUpdateRequest shopUpdateRequest) {
        boolean updated = shopService.updateShop(shopUpdateRequest);
        return ResultUtils.success(updated);
    }

    /**
     * 分页展示店铺的商品列表
     * @param userId
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/{userId}/products")
    @Operation(summary = "分页展示店铺的商品列表")
    public BaseResponse<PageVO<ProductVO>> listShopProductsByPage(@PathVariable Integer userId,
                                                               @RequestParam(defaultValue = "1") long current,
                                                               @RequestParam(defaultValue = "10") long size) {
        List<Product> shopProduct = productService.getByProviderId(userId);
        List<ProductVO> productVO = productService.getProductVO(shopProduct);
        IPage<ProductVO> page = new Page<>(current, size);
        page.setRecords(productVO);
        PageVO<ProductVO> shopProductPageVO = new PageVO<>(page.getRecords(), page.getTotal());
        return ResultUtils.success(shopProductPageVO);
    }

}
