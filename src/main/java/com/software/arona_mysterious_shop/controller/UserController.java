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
import com.software.arona_mysterious_shop.model.dto.user.*;
import com.software.arona_mysterious_shop.model.entity.User;
import com.software.arona_mysterious_shop.service.UserService;
import com.software.arona_mysterious_shop.utils.ThreadLocalUtil;
import com.software.arona_mysterious_shop.model.vo.LoginUserVO;
import com.software.arona_mysterious_shop.model.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api("用户接口")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userName = userRegisterRequest.getUserName();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userRole = userRegisterRequest.getUserRole();
        if (StringUtils.isAnyBlank(userAccount, userName, userPassword, checkPassword, userRole)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userAccount, userName, userPassword, checkPassword,userRole);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @param request          请求
     * @return {@link BaseResponse}<{@link LoginUserVO}>
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }



//    /**
//     * 用户登录（公众号扫码登录，前端轮询请求）
//     *
//     * @param userLoginRequest 用户登录请求
//     * @param request          请求
//     * @return {@link BaseResponse}<{@link LoginUserVO}>
//     */
//    @PostMapping("/login/wx_mp")
//    @ApiOperation(value = "用户登录（公众号扫码登录，前端轮询请求）")
//    public BaseResponse<LoginUserVO> userLoginByWxMp(@RequestBody UserLoginByWxMpRequest userLoginRequest, HttpServletRequest request) {
//        if (userLoginRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        String scene = userLoginRequest.getScene();
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("scene", scene);
//        User user = userService.getOne(queryWrapper);
//        if (user != null) {
//            log.info("user login succeed, id = {}", user.getId());
//            request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
//        }
//        LoginUserVO loginUserVO = userService.getLoginUserVO(user);
//        return ResultUtils.success(loginUserVO);
//    }



    /**
     * 用户注销
     *
     * @param request 请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户退出登录")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @return {@link BaseResponse}<{@link LoginUserVO}>
     */
    @GetMapping("/get/login")
    @ApiOperation(value = "获取当前登录用户")
    public BaseResponse<LoginUserVO> getLoginUser() {
        User user = ThreadLocalUtil.getLoginUser();
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    /**
     * 删除用户
     *
     * @param deleteRequest 删除请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation(value = "删除用户")
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }


    /**
     * 根据 id 获取用户（仅管理员）
     *
     * @param id 编号
     * @return {@link BaseResponse}<{@link User}>
     */
    @GetMapping("/get")
    @ApiOperation(value = "获取用户by id（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 根据 id 获取包装类
     *
     * @param id 编号
     * @return {@link BaseResponse}<{@link UserVO}>
     */
    @GetMapping("/get/vo")
    @ApiOperation(value = "获取用户VO by id")
    public BaseResponse<UserVO> getUserVOById(long id) {
        BaseResponse<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }

    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest 用户查询请求
     * @return {@link BaseResponse}<{@link Page}<{@link User}>>
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "分页获取用户列表（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryRequest));
        return ResultUtils.success(userPage);
    }

    /**
     * 分页获取用户封装列表
     *
     * @param userQueryRequest 用户查询请求
     * @return {@link BaseResponse}<{@link Page}<{@link UserVO}>>
     */
    @PostMapping("/list/page/vo")
    @ApiOperation(value = "分页获取用户VO列表")
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<User> userPage = userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
        List<UserVO> userVO = userService.getUserVO(userPage.getRecords());
        userVOPage.setRecords(userVO);
        return ResultUtils.success(userVOPage);
    }


}
