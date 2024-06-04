package com.software.arona_mysterious_shop.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.arona_mysterious_shop.common.ErrorCode;
import com.software.arona_mysterious_shop.exception.BusinessException;
import com.software.arona_mysterious_shop.exception.ThrowUtils;
import com.software.arona_mysterious_shop.manager.RuleConfigManager;
import com.software.arona_mysterious_shop.model.entity.GoodsInfo;
import com.software.arona_mysterious_shop.model.enums.GoodsTypeEnum;
import com.software.arona_mysterious_shop.model.vo.GoodsInfoVO;
import com.software.arona_mysterious_shop.service.GoodsInfoService;
import com.software.arona_mysterious_shop.mapper.GoodsInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 29967
* @description 针对表【goods_info(产品)】的数据库操作Service实现
* @createDate 2024-06-03 14:34:50
*/
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo>
        implements GoodsInfoService {

    @Resource
    private RuleConfigManager ruleConfigManager;

    @Override
    public void validGoodsInfo(GoodsInfo goodsInfo, boolean add) {
        if (goodsInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = goodsInfo.getName();
        Integer stock = goodsInfo.getStock();
        String type = goodsInfo.getType();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name), ErrorCode.PARAMS_ERROR,"产品名称不能为空");
        }
        // 有参数则校验
        ThrowUtils.throwIf(stock == null || stock < 0, ErrorCode.PARAMS_ERROR,"库存不能小于0");
        if (StringUtils.isNotBlank(name) && name.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
        if (StringUtils.isNotBlank(type)) {
            GoodsTypeEnum.getEnumByValue(type);
        }
    }

    @Override
    public Page<GoodsInfoVO> getGoodsInfoVOPage(Page<GoodsInfo> goodsInfoPage) {

        List<GoodsInfo> goodsInfoList = goodsInfoPage.getRecords();
        Page<GoodsInfoVO> drawAppVOPage = new Page<>(goodsInfoPage.getCurrent(), goodsInfoPage.getSize(), goodsInfoPage.getTotal());
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return drawAppVOPage;
        }
        // 填充信息
        List<GoodsInfoVO> drawAppVOList = goodsInfoList.stream().map(this::getGoodsInfoVO).collect(Collectors.toList());
        drawAppVOPage.setRecords(drawAppVOList);
        return drawAppVOPage;
    }

    @Override
    public GoodsInfoVO getGoodsInfoVO(GoodsInfo goodsInfo) {
        if (goodsInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 转换VO
        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
        return null;
    }
}


