package com.software.arona_mysterious_shop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.arona_mysterious_shop.model.entity.GoodsInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.arona_mysterious_shop.model.vo.GoodsInfoVO;

import java.util.List;

/**
* @author 29967
* @description 针对表【goods_info(产品)】的数据库操作Service
* @createDate 2024-06-03 14:34:50
*/
public interface GoodsInfoService extends IService<GoodsInfo> {

    /**
     * 检查商品信息合法性
     * @param goodsInfo
     */
    void validGoodsInfo(GoodsInfo goodsInfo, boolean add);

    /**
     * 分页获取商品VO
     * @param goodsInfoPage
     * @return
     */
    Page<GoodsInfoVO> getGoodsInfoVOPage(Page<GoodsInfo> goodsInfoPage);

    /**
     * 获取商品VO
     * @param goodsInfo
     * @return
     */
    GoodsInfoVO getGoodsInfoVO(GoodsInfo goodsInfo);

    /**
     * 获取商品类型列表
     * @param queryWrapper
     * @return
     */
    List<String> getTypes(QueryWrapper<GoodsInfo> queryWrapper);
}