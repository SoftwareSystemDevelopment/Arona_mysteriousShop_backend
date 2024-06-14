package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.shop.ShopUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Shop;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ProductVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ShopVO;

import java.util.List;

/**
* @author 29967
* @description 针对表【shop】的数据库操作Service
* @createDate 2024-06-07 10:39:04
*/
public interface ShopService extends IService<Shop> {

    /**
     * 更新店铺信息
     * @param shopUpdateRequest
     */
    boolean updateShop(ShopUpdateRequest shopUpdateRequest);

    /**
     * 获取店铺信息
     * @param userId
     * @return
     */
    ShopVO getShopVO(Integer userId);
}
