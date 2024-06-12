package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.service.UniversalImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@Tag(name = "图片上传接口")
public class UniversalImageController {

    @Resource
    private UniversalImageService productImageService;

    @PostMapping("/upload/product")
    @Operation(summary = "上传商品图片")
    public BaseResponse<String> uploadProductImage(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("productId") Integer productId) {
        String imageUrl = productImageService.uploadProductImage(file, productId);
        return ResultUtils.success(imageUrl);
    }

    @PostMapping("/upload/user")
    @Operation(summary = "上传用户头像")
    public BaseResponse<String> uploadUserAvatar(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("userId") Integer userId) {
        String imageUrl = productImageService.uploadUserAvatar(file, userId);
        return ResultUtils.success(imageUrl);
    }

    @GetMapping("/get/product")
    @Operation(summary = "获取图片")
    public BaseResponse<String> getImage(@RequestParam("productId") Integer productId) {
        String imageUrl = productImageService.getProductImageSrc(productId);
        return ResultUtils.success(imageUrl);
    }

    @GetMapping("/get/user")
    @Operation(summary = "获取头像")
    public BaseResponse<String> getUserAvatar(@RequestParam("userId") Integer userId) {
        String imageUrl = productImageService.getUserImageSrc(userId);
        return ResultUtils.success(imageUrl);
    }
}