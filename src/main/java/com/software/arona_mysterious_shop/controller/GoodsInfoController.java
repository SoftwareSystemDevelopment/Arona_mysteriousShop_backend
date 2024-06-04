package com.software.arona_mysterious_shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.arona_mysterious_shop.annotation.AuthCheck;
import com.software.arona_mysterious_shop.common.BaseResponse;
import com.software.arona_mysterious_shop.common.DeleteRequest;
import com.software.arona_mysterious_shop.common.ErrorCode;
import com.software.arona_mysterious_shop.common.ResultUtils;
import com.software.arona_mysterious_shop.constant.UserConstant;
import com.software.arona_mysterious_shop.exception.BusinessException;
import com.software.arona_mysterious_shop.exception.ThrowUtils;
import com.software.arona_mysterious_shop.model.entity.GoodsInfo;
import com.software.arona_mysterious_shop.model.entity.User;
import com.software.arona_mysterious_shop.service.GoodsInfoService;
import com.software.arona_mysterious_shop.service.UserService;
import com.software.arona_mysterious_shop.model.dto.goodsInfo.GoodsInfoAddRequest;
import com.software.arona_mysterious_shop.model.dto.goodsInfo.GoodsInfoQueryRequest;
import com.software.arona_mysterious_shop.model.dto.goodsInfo.GoodsInfoUpdateRequest;
import com.software.arona_mysterious_shop.model.enums.GoodsInfoStatusEnum;
import com.software.arona_mysterious_shop.model.vo.GoodsInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 产品接口
 *
 */
@RestController
@RequestMapping("/goodsInfo")
@Slf4j
@Api(tags = "产品接口")
public class GoodsInfoController {


    @Resource
    private GoodsInfoService goodsInfoService;

    @Resource
    private UserService userService;

    /**
     * 创建
     *
     * @param goodsInfoAddRequest 产品添加请求
     * @param request                  请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加产品")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<Long> addGoodsInfo(@RequestBody GoodsInfoAddRequest goodsInfoAddRequest, HttpServletRequest request) {
        if (goodsInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        GoodsInfo goodsInfo = new GoodsInfo();
        String name = goodsInfoAddRequest.getName();
        String cover = goodsInfoAddRequest.getCover();
        Integer price = goodsInfoAddRequest.getPrice();
        Integer stock = goodsInfoAddRequest.getStock();
        String types = goodsInfoAddRequest.getTypes();
        Integer status = goodsInfoAddRequest.getStatus();
        if(StringUtils.isAnyBlank(name, cover, types) || price == null || stock == null || status == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误，有参数为空");
        }
        BeanUtils.copyProperties(goodsInfoAddRequest, goodsInfo);
        goodsInfoService.validGoodsInfo(goodsInfo, true);
        User loginUser = userService.getLoginUser(request);
        goodsInfo.setUserId(loginUser.getId());
        boolean result = goodsInfoService.save(goodsInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newGoodsInfoId = goodsInfo.getId();
        return ResultUtils.success(newGoodsInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest 删除请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除产品")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<Boolean> deleteGoodsInfo(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        GoodsInfo oldGoodsInfo = goodsInfoService.getById(id);
        if (oldGoodsInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean b = goodsInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（供货商）
     *
     * @param goodsInfoUpdateRequest 产品更新请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新产品")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<Boolean> updateGoodsInfo(@RequestBody GoodsInfoUpdateRequest goodsInfoUpdateRequest) {
        if (goodsInfoUpdateRequest == null || goodsInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        GoodsInfo goodsInfo = new GoodsInfo();
        ThrowUtils.throwIf(ObjectUtils.isEmpty(goodsInfoUpdateRequest.getTypes()), ErrorCode.PARAMS_ERROR, "分类不能为空");
        BeanUtils.copyProperties(goodsInfoUpdateRequest, goodsInfo);
        // 参数校验
        goodsInfoService.validGoodsInfo(goodsInfo, false);
        long id = goodsInfoUpdateRequest.getId();
        // 判断是否存在
        GoodsInfo oldGoodsInfo = goodsInfoService.getById(id);
        if (oldGoodsInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean result = goodsInfoService.updateById(goodsInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id 编号
     * @return {@link BaseResponse}<{@link GoodsInfo}>
     */
    @GetMapping("/get")
    @ApiOperation(value = "获取产品 by id")
    public BaseResponse<GoodsInfo> getGoodsInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        GoodsInfo goodsInfo = goodsInfoService.getById(id);
        if (goodsInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(goodsInfo);
    }

    /**
     * 获取列表（仅供货商可使用）
     *
     * @param goodsInfoQueryRequest 产品查询请求
     * @return {@link BaseResponse}<{@link List}<{@link GoodsInfo}>>
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取产品列表")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<List<GoodsInfo>> listGoodsInfo(@RequestBody GoodsInfoQueryRequest goodsInfoQueryRequest) {
        List<GoodsInfo> goodsInfoList = goodsInfoService.list(getQueryWrapper(goodsInfoQueryRequest));
        return ResultUtils.success(goodsInfoList);
    }

    /**
     * 分页获取列表（仅供货商可使用）
     *
     * @param goodsInfoQueryRequest 产品查询请求
     * @param request                    请求
     * @return {@link BaseResponse}<{@link Page}<{@link GoodsInfo}>>
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "分页获取产品列表")
    @AuthCheck(mustRole = UserConstant.PROVIDER_ROLE)
    public BaseResponse<Page<GoodsInfo>> listGoodsInfoByPage(@RequestBody GoodsInfoQueryRequest goodsInfoQueryRequest,
                                                                       HttpServletRequest request) {
        long current = goodsInfoQueryRequest.getCurrent();
        long size = goodsInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<GoodsInfo> goodsInfoPage = goodsInfoService.page(new Page<>(current, size),
                getQueryWrapper(goodsInfoQueryRequest));
        return ResultUtils.success(goodsInfoPage);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param goodsInfoQueryRequest 产品查询请求
     * @return {@link BaseResponse}<{@link Page}<{@link GoodsInfoVO}>>
     */
    @PostMapping("/list/page/vo")
    @ApiOperation(value = "分页获取产品列表（封装类）")
    public BaseResponse<Page<GoodsInfoVO>> listGoodsInfoVOByPage(@RequestBody GoodsInfoQueryRequest goodsInfoQueryRequest) {
        long current = goodsInfoQueryRequest.getCurrent();
        long size = goodsInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        QueryWrapper<GoodsInfo> queryWrapper = this.getQueryWrapper(goodsInfoQueryRequest);
        // 只返回可以浏览的产品
        queryWrapper.eq("status", GoodsInfoStatusEnum.ENABLED.getValue());
        queryWrapper.orderByDesc("createTime");
        Page<GoodsInfo> goodsInfoPage = goodsInfoService.page(new Page<>(current, size),
                queryWrapper);
        return ResultUtils.success(goodsInfoService.getGoodsInfoVOPage(goodsInfoPage));
    }

    @GetMapping("/list/type")
    public BaseResponse<List<String>> listGoodsInfoType() {
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct type");
        List<String> typeList = goodsInfoService.listObjs(queryWrapper, obj -> (String) obj);
        return ResultUtils.success(typeList);
    }

    /**
     * 获取查询包装类
     *
     * @param goodsInfoQueryRequest 产品查询请求
     * @return {@link QueryWrapper}<{@link GoodsInfo}>
     */
    private QueryWrapper<GoodsInfo> getQueryWrapper(GoodsInfoQueryRequest goodsInfoQueryRequest) {
        if (goodsInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = goodsInfoQueryRequest.getId();
        String name = goodsInfoQueryRequest.getName();
        Integer price = goodsInfoQueryRequest.getPrice();
        Integer stock = goodsInfoQueryRequest.getStock();
        String types = goodsInfoQueryRequest.getTypes();
        Integer status = goodsInfoQueryRequest.getStatus();
        Long userId = goodsInfoQueryRequest.getUserId();
        List<String> ascSortField = goodsInfoQueryRequest.getAscSortField();
        List<String> descSortField = goodsInfoQueryRequest.getDescSortField();

        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.eq(price != null, "price", price);
        queryWrapper.eq(stock != null, "stock", stock);
        queryWrapper.eq(ObjectUtils.isNotEmpty(types), "type", types);
        queryWrapper.eq(status != null, "status", status);
        queryWrapper.eq(userId != null, "userId", userId);

        // MyBatis-plus 自带 columnToSqlSegment 方法进行注入过滤处理，不需要SqlUtils.validSortField(sortField)
        boolean ascValid = ascSortField != null && !ascSortField.isEmpty();
        boolean descValid = descSortField != null && !descSortField.isEmpty();
        queryWrapper.orderByAsc(ascValid, ascSortField);
        queryWrapper.orderByDesc(descValid, descSortField);

        return queryWrapper;

    }

}
