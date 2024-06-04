package com.software.arona_mysterious_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.software.arona_mysterious_shop.common.ErrorCode;
import com.software.arona_mysterious_shop.exception.BusinessException;
import com.software.arona_mysterious_shop.exception.ThrowUtils;
import com.software.arona_mysterious_shop.model.entity.GoodsInfo;
import com.software.arona_mysterious_shop.model.enums.GoodsTypeEnum;
import com.software.arona_mysterious_shop.model.vo.GoodsInfoVO;
import com.software.arona_mysterious_shop.service.GoodsInfoService;
import com.software.arona_mysterious_shop.mapper.GoodsInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Override
    public void validGoodsInfo(GoodsInfo goodsInfo, boolean add) {
        if (goodsInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = goodsInfo.getName();
        Integer price = goodsInfo.getPrice();
        Integer stock = goodsInfo.getStock();
        String types = goodsInfo.getTypes();
        Integer status = goodsInfo.getStatus();

        ThrowUtils.throwIf(stock < 0, ErrorCode.PARAMS_ERROR,"库存不能小于0");
        ThrowUtils.throwIf(price < 0, ErrorCode.PARAMS_ERROR,"价格不能小于0");
        ThrowUtils.throwIf(status != 0 && status != 1, ErrorCode.PARAMS_ERROR,"状态错误");

        //名称相关进行检验
        if(StringUtils.isNotBlank((name))) {
            //名称不能重复
            GoodsInfo goodsInfoByName = this.lambdaQuery().eq(GoodsInfo::getName, name).one();
            if (goodsInfoByName != null && !goodsInfoByName.getId().equals(goodsInfo.getId())) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "产品名称已存在");
            }
            //名称长度不能超过256
            if (name.length() > 256) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
            }
        }
        //类型相关检验
        Gson gson = new Gson();
        try {
            List<String> typeList = gson.fromJson(types, List.class);
            for(String type : typeList) {
                if(!GoodsTypeEnum.contains(type)) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "产品类型错误");
                }
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "产品类型错误");
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

    @Override
    public List<String> getTypes(QueryWrapper<GoodsInfo> queryWrapper) {
        List<String> typeList = new ArrayList<>();
        //找types字段不为空的所有记录
        for (GoodsInfo goodsInfo : this.list(queryWrapper)) {
            String types = goodsInfo.getTypes();
            if (StringUtils.isNotBlank(types)) {
                Gson gson = new Gson();
                List<String> typesList = gson.fromJson(types, List.class);
                //如果有重复则不添加
                for (String type : typesList) {
                    if (!typeList.contains(type)) {
                        typeList.add(type);
                    }
                }
            }
        }
        return typeList;
    }
}


