package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.ShopMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.shop.ShopUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Product;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Shop;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.PageVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.ProductVO;
import com.software_system_development.arona_mysterious_shop_backend.service.ProductService;
import com.software_system_development.arona_mysterious_shop_backend.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 29967
* @description 针对表【shop】的数据库操作Service实现
* @createDate 2024-06-07 10:39:04
*/
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop>
    implements ShopService{

    @Transactional
    @Override
    public boolean updateShop(ShopUpdateRequest shopUpdateRequest) {
        if (shopUpdateRequest == null || shopUpdateRequest.getShopId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "店铺ID不能为空");
        }
        // 查询店铺信息
        Shop shop = getById(shopUpdateRequest.getShopId());
        if (shop == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "店铺不存在");
        }
        // 更新店铺名称和描述
        if (StringUtils.isNotBlank(shopUpdateRequest.getName())) {
            shop.setName(shopUpdateRequest.getName());
        }
        if (StringUtils.isNotBlank(shopUpdateRequest.getDescription())) {
            shop.setDescription(shopUpdateRequest.getDescription());
        }
        // 执行更新
        return updateById(shop);
    }

}




