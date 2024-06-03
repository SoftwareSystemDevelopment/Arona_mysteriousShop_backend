package com.software.arona_mysterious_shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.arona_mysterious_shop.model.entity.GoodsInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.arona_mysterious_shop.model.vo.goodsInfo.GoodsInfoVO;

/**
* @author 29967
* @description 针对表【goods_info(产品)】的数据库操作Service
* @createDate 2024-06-03 14:34:50
*/
public interface GoodsInfoService extends IService<GoodsInfo> {


    void validGoodsInfo(GoodsInfo goodsInfo, boolean add);

    Page<GoodsInfoVO> getGoodsInfoVOPage(Page<GoodsInfo> goodsInfoPage);

    GoodsInfoVO getGoodsInfoVO(GoodsInfo goodsInfo);
}