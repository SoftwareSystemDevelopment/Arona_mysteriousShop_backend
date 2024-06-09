package com.software_system_development.arona_mysterious_shop_backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserLoginRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserRegisterRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
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
    int userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     * @param userLoginRequest 用户登录请求
     * @param request
     * @return 脱敏后的用户信息
     */
    UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 更新用户信息
     * @param userUpdateRequest
     * @param request
     * @return
     */
    int userUpdate(UserUpdateRequest userUpdateRequest, HttpServletRequest request);

    /**
     * 用户注销
     * @param request
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    UserVO getCurrentUser(HttpServletRequest request);

    /**
     * 根据条件查询用户列表
     * @param userId
     * @param userAccount
     * @param userName
     * @param userRole
     * @param current
     * @param size
     * @return
     */
    IPage<User> searchUsers(Integer userId, String userAccount, String userName, String userRole, long current, long size);

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
     *  用户鉴权
     * @param user
     * @return
     */
    boolean isAdmin(UserVO user);

    /**
     * 获取用户购物车信息
     */
    Integer getCartId(UserVO user);
}
