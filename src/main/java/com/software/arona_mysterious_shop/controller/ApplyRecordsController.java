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
import com.software.arona_mysterious_shop.model.dto.applyrecords.*;
import com.software.arona_mysterious_shop.model.entity.ApplyRecords;
import com.software.arona_mysterious_shop.model.entity.GoodsInfo;
import com.software.arona_mysterious_shop.model.entity.User;
import com.software.arona_mysterious_shop.service.ApplyRecordsService;
import com.software.arona_mysterious_shop.service.GoodsInfoService;
import com.software.arona_mysterious_shop.service.UserService;
import com.software.arona_mysterious_shop.model.enums.ApplyStatusEnum;
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
 * 申请产品接口
 *
 */
@RestController
@RequestMapping("/applyRecords")
@Slf4j
public class ApplyRecordsController {


    @Resource
    private ApplyRecordsService applyRecordsService;

    @Resource
    private UserService userService;


    @Resource
    private GoodsInfoService goodsInfoService;


    @Resource
    private RedissonClient redissonClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param applyRecordsAddRequest 产品添加请求
     * @param request                请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addApplyRecords(@RequestBody ApplyRecordsAddRequest applyRecordsAddRequest, HttpServletRequest request) {
        if (applyRecordsAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApplyRecords applyRecords = new ApplyRecords();
        BeanUtils.copyProperties(applyRecordsAddRequest, applyRecords);
        applyRecordsService.validApplyRecords(applyRecords, true);
        User loginUser = userService.getLoginUser(request);
        applyRecords.setApplicantId(loginUser.getId());
        boolean result = applyRecordsService.save(applyRecords);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newApplyRecordsId = applyRecords.getId();
        return ResultUtils.success(newApplyRecordsId);
    }

    /**
     * 删除
     *
     * @param deleteRequest 删除请求
     * @param request       请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteApplyRecords(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        ApplyRecords oldApplyRecords = applyRecordsService.getById(id);
        if (oldApplyRecords == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean b = applyRecordsService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param applyRecordsUpdateRequest 产品更新请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateApplyRecords(@RequestBody ApplyRecordsUpdateRequest applyRecordsUpdateRequest) {
        if (applyRecordsUpdateRequest == null || applyRecordsUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApplyRecords applyRecords = new ApplyRecords();
        BeanUtils.copyProperties(applyRecordsUpdateRequest, applyRecords);
        // 参数校验
        applyRecordsService.validApplyRecords(applyRecords, false);
        long id = applyRecordsUpdateRequest.getId();
        // 判断是否存在
        ApplyRecords oldApplyRecords = applyRecordsService.getById(id);
        if (oldApplyRecords == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean result = applyRecordsService.updateById(applyRecords);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id 编号
     * @return {@link BaseResponse}<{@link ApplyRecords}>
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<ApplyRecords> getApplyRecordsById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApplyRecords applyRecords = applyRecordsService.getById(id);
        if (applyRecords == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(applyRecords);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param applyRecordsQueryRequest 产品查询请求
     * @return {@link BaseResponse}<{@link List}<{@link ApplyRecords}>>
     */
    @PostMapping("/list")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<ApplyRecords>> listApplyRecords(@RequestBody ApplyRecordsQueryRequest applyRecordsQueryRequest) {
        List<ApplyRecords> applyRecordsList = applyRecordsService.list(getQueryWrapper(applyRecordsQueryRequest));
        return ResultUtils.success(applyRecordsList);
    }

    /**
     * 分页获取列表（仅管理员可使用）
     *
     * @param applyRecordsQueryRequest 产品查询请求
     * @param request                  请求
     * @return {@link BaseResponse}<{@link Page}<{@link ApplyRecords}>>
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<ApplyRecords>> listApplyRecordsByPage(@RequestBody ApplyRecordsQueryRequest applyRecordsQueryRequest,
                                                                   HttpServletRequest request) {
        long current = applyRecordsQueryRequest.getCurrent();
        long size = applyRecordsQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<ApplyRecords> applyRecordsPage = applyRecordsService.page(new Page<>(current, size),
                getQueryWrapper(applyRecordsQueryRequest));
        return ResultUtils.success(applyRecordsPage);
    }


    @PostMapping("/apply")
    @Transactional
    public BaseResponse<Long> applyApplyRecords(@RequestBody ApplyRecordsApplyRequest applyRecordsApplyRequest, HttpServletRequest request) {
        if (applyRecordsApplyRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int nums = applyRecordsApplyRequest.getApplyNums();
        long goodsId = applyRecordsApplyRequest.getGoodsId();
        GoodsInfo goodsInfo = goodsInfoService.getById(goodsId);
        if (goodsInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "周边信息不存在");
        }
        //获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        if (!userService.hasInternalAuth(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        ApplyRecords applyRecords = new ApplyRecords();
        //使用redisson获取分布式锁
        String lockKey = "apply_lock" + goodsId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                {
                    // 查出周边让库存-nums

                    // 检查库存是否足够
                    if (nums > goodsInfo.getStock()) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "库存不足");
                    }

                    if (goodsInfo.getStock() <= 0) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "库存不足");
                    }
                    // 更新周边信息库存（使用乐观锁）
                    goodsInfo.setStock(goodsInfo.getStock() - nums);
                    boolean updateResult = goodsInfoService.updateById(goodsInfo);
                    if (!updateResult) {
                        throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新周边库存失败");
                    }
                }
                // 将申请状态改成审核中 并插入数据
                BeanUtils.copyProperties(applyRecordsApplyRequest, applyRecords);
                applyRecords.setApplicantId(loginUser.getId());
                applyRecords.setGoodsName(goodsInfo.getName());
                applyRecords.setApplicantUserName(loginUser.getUserName());
                applyRecords.setStatus(ApplyStatusEnum.PENDING.getValue());
//                applyRecords.setApplyNums(nums);
                applyRecords.setApplicationTime(new Date());
                boolean result = applyRecordsService.save(applyRecords);
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

        return ResultUtils.success(applyRecords.getId());
    }


    @PostMapping("/approve")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> approveApplyRecords(@RequestBody ApplyRecordsApproveRequest applyRecordsApproveRequest, HttpServletRequest request) {
        if (applyRecordsApproveRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        long applyRecordsId = applyRecordsApproveRequest.getId();
        String reason = applyRecordsApproveRequest.getReason();
        //获取申请信息
        ApplyRecords applyRecords = applyRecordsService.getById(applyRecordsId);

        // 判断申请记录的当前状态是否为“审核中”
        if (ApplyStatusEnum.PENDING.getValue() != applyRecords.getStatus()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "申请记录状态无效");
        }

        if (applyRecordsApproveRequest.getApproved()) {
            applyRecords.setStatus(ApplyStatusEnum.APPROVED.getValue());
        } else {

            applyRecords.setStatus(ApplyStatusEnum.REJECTED.getValue());
            GoodsInfo goodsInfo = goodsInfoService.getById(applyRecords.getGoodsId());
            if (goodsInfo != null) {
//                goodsInfo.setStock(goodsInfo.getStock() + applyRecords.getApplyNums());
                goodsInfoService.updateById(goodsInfo);
            }
        }
        applyRecords.setReason(reason);
        applyRecords.setAdminId(loginUser.getId());
        applyRecordsService.updateById(applyRecords);
        return ResultUtils.success(applyRecords.getId());
    }


    @GetMapping("getStatus")
    @Deprecated
    public BaseResponse<Integer> getStatus(long id, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        long userId = loginUser.getId();

        QueryWrapper<ApplyRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goodsId", id);
        queryWrapper.eq("applicantId", userId);
        ApplyRecords applyRecords = applyRecordsService.getOne(queryWrapper);
        int status = applyRecords.getStatus();

        return ResultUtils.success(status);
    }


    @PostMapping("getAllStatus")
    @Deprecated
    public BaseResponse<Map<Long, Integer>> getAllStatus(@RequestBody ApplyStatusRequest applyStatusRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        long userId = loginUser.getId();
        List<Long> ids = applyStatusRequest.getIds();
        QueryWrapper<ApplyRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("goodsId", ids);
        queryWrapper.eq("applicantId", userId);
        List<ApplyRecords> applyRecordsList = applyRecordsService.list(queryWrapper.select("goodsId", "status"));
        Map<Long, Integer> statusMap = applyRecordsList.stream()
                .collect(Collectors.toMap(ApplyRecords::getGoodsId, ApplyRecords::getStatus));

        return ResultUtils.success(statusMap);
    }

    /*
     * 获取内部员工申请记录
     */
    @GetMapping("getApplyRecords")
    public BaseResponse<List<ApplyRecords>> getApplyRecords(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (!userService.hasInternalAuth(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "不是内部员工");
        }
        long userId = loginUser.getId();
        QueryWrapper<ApplyRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("applicantId", userId);
        List<ApplyRecords> applyRecordsList = applyRecordsService.list(queryWrapper);
        return ResultUtils.success(applyRecordsList);
    }


    // endregion

    /**
     * 获取查询包装类
     *
     * @param applyRecordsQueryRequest 产品查询请求
     * @return {@link QueryWrapper}<{@link ApplyRecords}>
     */
    private QueryWrapper<ApplyRecords> getQueryWrapper(ApplyRecordsQueryRequest applyRecordsQueryRequest) {
        if (applyRecordsQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long id = applyRecordsQueryRequest.getId();
        Long goodsId = applyRecordsQueryRequest.getGoodsId();
        String goodsName = applyRecordsQueryRequest.getGoodsName();
        Long applicantId = applyRecordsQueryRequest.getApplicantId();
        String applicantUserName = applyRecordsQueryRequest.getApplicantUserName();
        Integer status = applyRecordsQueryRequest.getStatus();
        String content = applyRecordsQueryRequest.getContent();
        String reason = applyRecordsQueryRequest.getReason();
        List<String> ascSortField = applyRecordsQueryRequest.getAscSortField();
        List<String> descSortField = applyRecordsQueryRequest.getDescSortField();


        QueryWrapper<ApplyRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(goodsId != null, "goodsId", goodsId);
        queryWrapper.eq(StringUtils.isNotBlank(goodsName), "goodsName", goodsName);
        queryWrapper.eq(StringUtils.isNotBlank(applicantUserName), "applicantUserName", applicantUserName);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.eq(StringUtils.isNotBlank(reason), "reason", reason);
        queryWrapper.eq(status != null, "status", status);
        queryWrapper.eq(applicantId != null, "applicantId", applicantId);

        // MyBatis-plus 自带 columnToSqlSegment 方法进行注入过滤处理，不需要SqlUtils.validSortField(sortField)
        boolean ascValid = ascSortField != null && ascSortField.size() > 0;
        boolean descValid = descSortField != null && descSortField.size() > 0;
        queryWrapper.orderByAsc(ascValid, ascSortField);
        queryWrapper.orderByDesc(descValid, descSortField);

        return queryWrapper;

    }

}
