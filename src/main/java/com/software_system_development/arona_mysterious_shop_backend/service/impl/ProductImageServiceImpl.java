package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.mapper.ProductImageMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.ProductImage;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductImageService;
import com.software_system_development.arona_mysterious_shop_backend.utils.FileUploadUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 29967
* @description 针对表【productimage】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class ProductImageServiceImpl extends ServiceImpl<ProductImageMapper, ProductImage>
    implements ProductImageService {
    @Resource
    private ProductImageMapper productImageMapper;

    @Override
    public String uploadProductImage(MultipartFile file, Integer productId) {
        // 1. 上传图片并保存到指定目录，获取图片地址
        String imageUrl = uploadImageAndGetUrl(file);

        // 2. 将图片地址保存到数据库
        ProductImage productImage = new ProductImage();
        productImage.setProductImageSrc(imageUrl);
        productImage.setProductImageProductId(productId);
        productImageMapper.insert(productImage);

        return imageUrl;
    }

    private String uploadImageAndGetUrl(MultipartFile file) {
        // 调用文件上传工具类上传文件
        return FileUploadUtil.uploadImageAndGetUrl(file);
    }
}




