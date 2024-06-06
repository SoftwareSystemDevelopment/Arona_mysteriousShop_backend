package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserLoginRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserQueryRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserRegisterRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 29967
* @description 针对表【user】的数据库操作Service
* @createDate 2024-06-05 12:28:45
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userRegisterRequest 用户注册请求
     * @return 新用户 id
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     * @param userLoginRequest 用户登录请求
     * @param request
     * @return 脱敏后的用户信息
     */
    UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 用户修改信息
     */
    Long userUpdate(UserUpdateRequest userUpdateRequest, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     */
    boolean userLogout(HttpServletRequest request);

    /**
     *  获取用户信息
     * @return
     */
    User getUser(HttpServletRequest request);
    User getUser(User user);
    List<User> getUser(List<User> records);

    /**
     * 获取脱敏的用户信息
     *
     * @param request
     * @return
     */
    UserVO getUserVO(HttpServletRequest request);
    UserVO getUserVO(User user);
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    boolean isAdmin(UserVO user);

    /**
     * 根据条件进行查询
     *
     * @param userQueryRequest 查询条件
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
