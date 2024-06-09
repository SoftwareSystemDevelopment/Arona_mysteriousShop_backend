package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/productImage")
@Tag(name = "图片上传接口")
public class ProductImageController {

    @Resource
    private ProductImageService productImageService;

    @PostMapping("/upload")
    @Operation(summary = "上传商品图片")
    public BaseResponse<String> uploadProductImage(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("productId") Integer productId) {
        String imageUrl = productImageService.uploadProductImage(file, productId);
        return ResultUtils.success(imageUrl);
    }
}