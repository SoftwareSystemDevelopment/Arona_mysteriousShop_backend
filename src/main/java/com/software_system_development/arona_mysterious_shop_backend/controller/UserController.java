package com.software_system_development.arona_mysterious_shop_backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software_system_development.arona_mysterious_shop_backend.annotation.AuthCheck;
import com.software_system_development.arona_mysterious_shop_backend.common.BaseResponse;
import com.software_system_development.arona_mysterious_shop_backend.exception.ThrowUtils;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.*;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.common.ResultUtils;
import com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
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
     * 综合查询用户信息
     * @param userId
     * @param userAccount
     * @param userName
     * @param userRole
     * @return
     */
    @GetMapping("/search")
    @Operation(summary = "综合查询用户信息")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<User>> searchUser(@RequestParam(required = false) Integer userId,
                                               @RequestParam(required = false) String userAccount,
                                               @RequestParam(required = false) String userName,
                                               @RequestParam(required = false) String userRole) {
        // 执行查询操作
        List<User> users = Collections.emptyList();
        if (userId != null) {
            User user = userService.getById(userId);
            if (user != null) {
                users = Collections.singletonList(user);
            }
        } else if (userAccount != null) {
            User user = userService.getByUserAccount(userAccount);
            if (user != null) {
                users = Collections.singletonList(user);
            }
        } else if (userName != null) {
            users = userService.getByUserName(userName);
        } else if (userRole != null) {
            users = userService.getByUserRole(userRole);
        }
        // 返回结果
        ThrowUtils.throwIf(users.isEmpty(), ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(users);
    }

    /**
     * 综合查询用户VO信息
     * @param userId
     * @param userAccount
     * @param userName
     * @param userRole
     * @return
     */
    @GetMapping("/search/vo")
    @Operation(summary = "综合查询用户VO信息")
    public BaseResponse<List<UserVO>> searchUserVO(@RequestParam(required = false) Integer userId,
                                             @RequestParam(required = false) String userAccount,
                                             @RequestParam(required = false) String userName,
                                             @RequestParam(required = false) String userRole) {
        BaseResponse<List<User>> response = searchUser(userId, userAccount, userName, userRole);
        List<User> users = response.getData();
        if (users == null || users.isEmpty()) {
            return ResultUtils.success(null);
        }
        return ResultUtils.success(userService.getUserVO(users));
    }

    /**
     * 获取用户列表（分页展示）
     *
     * @param current 当前页数
     * @param size    每页大小
     * @return {@link BaseResponse}<{@link List}<{@link User}>>
     */
    @GetMapping("/list")
    @Operation(summary = "获取用户列表（分页展示）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<User>> listUsers(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        Page<User> userPage = userService.page(new Page<>(current, size));
        return ResultUtils.success(userPage.getRecords());
    }

    /**
     * 获取用户VO列表（分页展示）
     *
     * @param current 当前页数
     * @param size    每页大小
     * @return {@link BaseResponse}<{@link List}<{@link UserVO}>>
     */
    @GetMapping("/list/vo")
    @Operation(summary = "获取用户VO列表（分页展示）")
    public BaseResponse<List<UserVO>> listUserVOs(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        Page<User> userPage = userService.page(new Page<>(current, size));
        List<UserVO> userVOList = userService.getUserVO(userPage.getRecords());
        return ResultUtils.success(userVOList);
    }
}
