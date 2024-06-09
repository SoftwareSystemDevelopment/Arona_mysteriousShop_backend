package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 29967
* @description 针对表【productimage】的数据库操作Service
* @createDate 2024-06-05 12:28:45
*/
public interface ProductImageService extends IService<ProductImage> {
    String uploadProductImage(MultipartFile file, Integer productId);
}
