package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.annotation.AuthCheck;
import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.exception.ThrowUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.*;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.PageVO;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "用户接口")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public BaseResponse<Integer> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @param request          请求
     * @return {@link BaseResponse}<{@link UserVO}>
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = userService.userLogin(userLoginRequest, request);
        return ResultUtils.success(userVO);
    }

    /**
     * 修改用户信息
     *
     */
    @PostMapping("/update")
    @Operation(summary = "修改用户信息")
    public BaseResponse<Integer> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        if (userUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userUpdate(userUpdateRequest, request);
        return ResultUtils.success(result);
    }

    /**
     * 用户注销
     *
     * @param request 请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/logout")
    @Operation(summary = "用户退出登录")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @DeleteMapping("/delete/{userId}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @Operation(summary = "删除用户")
    public BaseResponse<Boolean> deleteUser(@PathVariable long userId) {
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(userId);
        return ResultUtils.success(b);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/current")
    @Operation(summary = "获取当前登录用户信息")
    public BaseResponse<UserVO> getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = userService.getCurrentUser(request);
        return ResultUtils.success(userVO);
    }

    /**
     * 综合查询用户信息
     * @param userId
     * @param userAccount
     * @param userName
     * @param userRole
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "分页查询用户信息（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PageVO<User>> searchUser(@RequestParam(required = false) Integer userId,
                                                 @RequestParam(required = false) String userAccount,
                                                 @RequestParam(required = false) String userName,
                                                 @RequestParam(required = false) String userRole,
                                                 @RequestParam(defaultValue = "1") long current,
                                                 @RequestParam(defaultValue = "10") long size) {
        IPage<User> userPage = userService.searchUsers(userId, userAccount, userName, userRole, current, size);
        ThrowUtils.throwIf(userPage.getRecords().isEmpty(), ErrorCode.NOT_FOUND_ERROR);
        PageVO<User> userPageVO = new PageVO<>(userPage.getRecords(), userPage.getTotal());
        return ResultUtils.success(userPageVO);
    }


    /**
     * 综合查询用户VO信息
     * @param userId
     * @param userAccount
     * @param userName
     * @param userRole
     * @return
     */
    @GetMapping("/list/vo")
    @Operation(summary = "分页查询用户VO信息")
    public BaseResponse<PageVO<UserVO>> searchUserVO(@RequestParam(required = false) Integer userId,
                                                    @RequestParam(required = false) String userAccount,
                                                    @RequestParam(required = false) String userName,
                                                    @RequestParam(required = false) String userRole,
                                                    @RequestParam(defaultValue = "1") long current,
                                                    @RequestParam(defaultValue = "10") long size) {
        IPage<User> userPage = userService.searchUsers(userId, userAccount, userName, userRole, current, size);
        if (userPage.getRecords() == null || userPage.getRecords().isEmpty()) {
            return ResultUtils.success(null);
        }
        List<UserVO> userVOList = userService.getUserVO(userPage.getRecords());
        IPage<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
        userVOPage.setRecords(userVOList);
        PageVO<UserVO> userPageVO = new PageVO<>(userVOPage.getRecords(), userVOPage.getTotal());
        return ResultUtils.success(userPageVO);
    }
}
