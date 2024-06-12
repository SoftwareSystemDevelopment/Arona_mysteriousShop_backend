package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.mapper.UniversalImageMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.UniversalImage;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.UniversalImageService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import com.software_system_development.arona_mysterious_shop_backend.utils.FileUploadUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 29967
* @description 针对表【UniversalImage】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class UniversalImageServiceImpl extends ServiceImpl<UniversalImageMapper, UniversalImage>
    implements UniversalImageService {
    @Resource
    private UniversalImageMapper productImageMapper;

    @Override
    public String uploadProductImage(MultipartFile file, Integer productId) {
        // 1. 上传图片并保存到指定目录，获取图片地址
        String imageUrl = uploadImageAndGetUrl(file);

        // 2. 将图片地址保存到数据库
        UniversalImage productImage = new UniversalImage();
        productImage.setImageSrc(imageUrl);
        productImage.setProductId(productId);
        productImageMapper.insert(productImage);

        return imageUrl;
    }

    @Override
    public String uploadUserAvatar(MultipartFile file, Integer userId) {
        // 1. 上传图片并保存到指定目录，获取图片地址
        String imageUrl = uploadImageAndGetUrl(file);

        // 2. 将图片地址保存到数据库
        UniversalImage productImage = new UniversalImage();
        productImage.setImageSrc(imageUrl);
        productImage.setUserId(userId);
        productImageMapper.insert(productImage);

        return imageUrl;
    }

    @Override
    public String getProductImageSrc(Integer productId) {
        QueryWrapper<UniversalImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productId", productId);
        UniversalImage productImage = productImageMapper.selectOne(queryWrapper);
        if (productImage == null) {
            return null;
        }
        return productImage.getImageSrc();
    }

    @Override
    public String getUserImageSrc(Integer userId) {
        QueryWrapper<UniversalImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        UniversalImage productImage = productImageMapper.selectOne(queryWrapper);
        if (productImage == null) {
            return null;
        }
        return productImage.getImageSrc();
    }

    private String uploadImageAndGetUrl(MultipartFile file) {
        // 调用文件上传工具类上传文件
        return FileUploadUtil.uploadImageAndGetUrl(file);
    }
}




