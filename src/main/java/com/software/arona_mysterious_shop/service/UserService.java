package com.software.arona_mysterious_shop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software.arona_mysterious_shop.model.dto.user.UserQueryRequest;
import com.software.arona_mysterious_shop.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.arona_mysterious_shop.model.vo.LoginUserVO;
import com.software.arona_mysterious_shop.model.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 29967
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-06-03 14:34:50
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userName 用户名
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param userRole 用户类型：user/provider
     * @return 新用户 id
     */
    long userRegister(String userAccount,String userName,String userPassword, String checkPassword, String userRole);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);



    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);


    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

}
