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
import com.software.arona_mysterious_shop.model.dto.orderinfo.*;
import com.software.arona_mysterious_shop.model.entity.GoodsInfo;
import com.software.arona_mysterious_shop.model.entity.OrderInfo;
import com.software.arona_mysterious_shop.model.entity.User;
import com.software.arona_mysterious_shop.service.GoodsInfoService;
import com.software.arona_mysterious_shop.service.OrderInfoService;
import com.software.arona_mysterious_shop.service.OrdersGoodsService;
import com.software.arona_mysterious_shop.service.UserService;
import com.software.arona_mysterious_shop.model.enums.OrderStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 订单接口
 *
 */
@RestController
@RequestMapping("/orderinfo")
@Slf4j
@Api(tags = "订单接口")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private OrdersGoodsService ordersGoodsService;

    @Resource
    private UserService userService;


    @Resource
    private GoodsInfoService goodsInfoService;


    @Resource
    private RedissonClient redissonClient;

    /**
     * 创建
     *
     * @param orderAddRequest 新建订单请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/add")
    @ApiOperation("创建订单")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addApplyRecords(@RequestBody OrderAddRequest orderAddRequest) {
        if (orderAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        OrderInfo orderInfo = new OrderInfo();
        Long userId = orderAddRequest.getUserId();
        String userName = orderAddRequest.getUserName();
        Integer status = orderAddRequest.getStatus();
        if(StringUtils.isBlank(userName) || userId == null || status == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误，有参数为空");
        }
        BeanUtils.copyProperties(orderAddRequest, orderInfo);
        orderInfoService.validOrder(orderInfo, true);
        boolean result = orderInfoService.save(orderInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newOrderId = orderInfo.getId();
        return ResultUtils.success(newOrderId);
    }

    /**
     * 删除
     *
     * @param deleteRequest 删除请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @ApiOperation("删除订单")
    public BaseResponse<Boolean> deleteApplyRecords(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        long userId = deleteRequest.getUserId();
        // 判断是否存在
        OrderInfo oldOrder = orderInfoService.getById(id);
        if (oldOrder == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //检验是否有权限删除
        User loginUser = userService.getLoginUser(request);
        if(!userService.isAdmin(loginUser) && loginUser.getId() != userId) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "无权限删除");
        }
        boolean b = orderInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 根据 id 获取订单
     *
     * @param id 编号
     * @return {@link BaseResponse}<{@link OrderInfo}>
     */
    @GetMapping("/get")
    @ApiOperation("获取订单by id")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<OrderInfo> getOrderById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        OrderInfo orderInfo = orderInfoService.getById(id);
        if (orderInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(orderInfo);
    }

    /**
     * 获取订单列表（仅管理员可使用）
     *
     * @param orderQueryRequest 产品查询请求
     * @return {@link BaseResponse}<{@link List}<{@link OrderInfo}>>
     */
    @PostMapping("/list")
    @ApiOperation("获取订单列表")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<OrderInfo>> listApplyRecords(@RequestBody OrderQueryRequest orderQueryRequest) {
        List<OrderInfo> orderList = orderInfoService.list(getQueryWrapper(orderQueryRequest));
        return ResultUtils.success(orderList);
    }

    /**
     * 分页获取列表（仅管理员可使用）
     *
     * @param orderQueryRequest 产品查询请求
     * @param request                  请求
     * @return {@link BaseResponse}<{@link Page}<{@link OrderInfo}>>
     */
    @PostMapping("/list/page")
    @ApiOperation("分页获取订单列表")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<OrderInfo>> listApplyRecordsByPage(@RequestBody OrderQueryRequest orderQueryRequest,
                                                                   HttpServletRequest request) {
        long current = orderQueryRequest.getCurrent();
        long size = orderQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<OrderInfo>orderPage = orderInfoService.page(new Page<>(current, size),
                getQueryWrapper(orderQueryRequest));
        return ResultUtils.success(orderPage);
    }


    @PostMapping("/apply")
    @ApiOperation("下达订单")
    @Transactional
    public BaseResponse<Long> applyApplyRecords(@RequestBody OrderApplyRequest orderApplyRequest, HttpServletRequest request) {
        if (orderApplyRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int nums = orderApplyRequest.getApplyNums();
        long goodsId = orderApplyRequest.getGoodsId();
        GoodsInfo goodsInfo = goodsInfoService.getById(goodsId);
        if (goodsInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "产品信息不存在");
        }
        //获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        OrderInfo orderInfo = new OrderInfo();
        //使用redisson获取分布式锁
        String lockKey = "apply_lock" + goodsId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                {
                    // 查出产品让库存-nums

                    // 检查库存是否足够
                    if (nums > goodsInfo.getStock()) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "库存不足");
                    }

                    if (goodsInfo.getStock() <= 0) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "库存不足");
                    }
                    // 更新产品信息库存（使用乐观锁）
                    goodsInfo.setStock(goodsInfo.getStock() - nums);
                    boolean updateResult = goodsInfoService.updateById(goodsInfo);
                    if (!updateResult) {
                        throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新产品库存失败");
                    }
                }
                // 将申请状态改成审核中 并插入数据
                BeanUtils.copyProperties(orderApplyRequest, orderInfo);
                orderInfo.setUserId(loginUser.getId());
                orderInfo.setUserName(loginUser.getUserName());
                orderInfo.setStatus(OrderStatusEnum.PENDING.getValue());
                orderInfo.setOrderTime(new Date());
                boolean result = orderInfoService.save(orderInfo);
                if (!result) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR);
                }

            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

        return ResultUtils.success(orderInfo.getId());
    }


    @PostMapping("/approve")
    @ApiOperation("商家审核订单")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> approveApplyRecords(@RequestBody OrderApproveRequest orderApproveRequest, HttpServletRequest request) {
        if (orderApproveRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long orderId = orderApproveRequest.getId();
        int status = orderApproveRequest.getIsApproved();
        //获取申请信息
        OrderInfo orderInfo = orderInfoService.getById(orderId);

        // 判断申请记录的当前状态是否为“审核中”
        if (OrderStatusEnum.PENDING.getValue() != orderInfo.getStatus()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "订单状态无效");
        }

        if (status == 1) {
            orderInfo.setStatus(OrderStatusEnum.APPROVED.getValue());
        } else if(status == 2) {
            orderInfo.setStatus(OrderStatusEnum.REJECTED.getValue());
        }
        orderInfoService.updateById(orderInfo);
        return ResultUtils.success(orderInfo.getId());
    }


    @GetMapping("getStatus")
    @ApiOperation("获取订单申请状态")
    @Deprecated
    public BaseResponse<Integer> getStatus(long id, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        long userId = loginUser.getId();

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goodsId", id);
        queryWrapper.eq("userId", userId);
        OrderInfo orderInfo = orderInfoService.getOne(queryWrapper);
        int status = orderInfo.getStatus();

        return ResultUtils.success(status);
    }


    /**
     * 获取查询包装类
     *
     * @param orderQueryRequest 产品查询请求
     * @return {@link QueryWrapper}<{@link OrderInfo}>
     */
    private QueryWrapper<OrderInfo> getQueryWrapper(OrderQueryRequest orderQueryRequest) {
        if (orderQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long id = orderQueryRequest.getId();
        Long userId = orderQueryRequest.getUserId();
        String userName = orderQueryRequest.getUserName();
        Integer status = orderQueryRequest.getStatus();
        List<String> ascSortField = orderQueryRequest.getAscSortField();
        List<String> descSortField = orderQueryRequest.getDescSortField();


        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.eq(status != null, "status", status);
        queryWrapper.eq(userId != null, "applicantId", userId);

        // MyBatis-plus 自带 columnToSqlSegment 方法进行注入过滤处理，不需要SqlUtils.validSortField(sortField)
        boolean ascValid = ascSortField != null && ascSortField.size() > 0;
        boolean descValid = descSortField != null && descSortField.size() > 0;
        queryWrapper.orderByAsc(ascValid, ascSortField);
        queryWrapper.orderByDesc(descValid, descSortField);

        return queryWrapper;

    }

}
