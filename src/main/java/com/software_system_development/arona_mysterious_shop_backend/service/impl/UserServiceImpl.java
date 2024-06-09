package com.software_system_development.arona_mysterious_shop_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.mapper.UserMapper;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserLoginRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserRegisterRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.dto.user.UserUpdateRequest;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Cart;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.Shop;
import com.software_system_development.arona_mysterious_shop_backend.model.entity.User;
import com.software_system_development.arona_mysterious_shop_backend.model.enums.UserRoleEnum;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.CartService;
import com.software_system_development.arona_mysterious_shop_backend.service.ShopService;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.software_system_development.arona_mysterious_shop_backend.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 29967
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-06-05 12:28:45
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "arona";

    @Resource
    private UserMapper userMapper;

    @Resource
    private CartService cartService;

    @Resource
    ShopService shopService;

    @Override
    public int userRegister(UserRegisterRequest userRegisterRequest) {
        // 校验参数
        validateUserParams(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPassword(),
                userRegisterRequest.getUserName(), userRegisterRequest.getUserRole(), false);
        synchronized (userRegisterRequest.getUserAccount().intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userRegisterRequest.getUserAccount());
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userRegisterRequest.getUserPassword()).getBytes());
            User user = new User();

            Cart cart = new Cart();
            cart.setCreateTime(new Date());
            cart.setUpdateTime(new Date());
            boolean cartSaveResult = cartService.save(cart);
            if (!cartSaveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "购物车创建失败");
            }
            BeanUtils.copyProperties(userRegisterRequest, user);
            user.setUserPassword(encryptPassword);
            user.setUserCreateDate(new Date());
            user.setUserUpdateDate(new Date());
            user.setCartId(cart.getCartId());
            // 插入数据库
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }

            // 如果注册的角色是 provider，创建一个店铺
            if (UserConstant.PROVIDER_ROLE.equals(userRegisterRequest.getUserRole())) {
                Shop shop = new Shop();
                shop.setName(userRegisterRequest.getShopName()); // 店铺名称
                shop.setDescription("Default description"); // 设置默认描述
                shop.setShopUserId(user.getUserId()); // 关联用户ID
                shopService.save(shop); // 保存店铺
            }

            return user.getUserId();
        }
    }




    @Override
    public UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        // 1. 校验
        validateUserParams(userAccount, userPassword, null, null, true);
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }
        // 3. 用户脱敏
        UserVO safetyUser = getUserVO(user);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public int userUpdate(UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        UserVO currentUser = getUserVO(request);
        Integer userId = userUpdateRequest.getUserId();
        String userPassword = userUpdateRequest.getUserPassword();
        String userName = userUpdateRequest.getUserName();
        Date userUpdateDate = new Date();
        if (StringUtils.isAnyBlank(userPassword, userName) || userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 判断是否有权限修改，必须要是用户自己或者是管理员
        if (!currentUser.getUserId().equals(userId) && !isAdmin(currentUser)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无权限修改");
        }
        // 修改用户信息
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        user.setUserPassword(encryptPassword);
        user.setUserUpdateDate(userUpdateDate);
        boolean updateResult = this.updateById(user);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改失败");
        }
        // 如果是修改自己的信息，则更新session
        if (currentUser.getUserId().equals(userId)) {
            User updatedUser = this.getById(userId);
            UserVO safetyUser = getUserVO(updatedUser);
            request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        }
        return userId;
    }


    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public UserVO getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return (UserVO) userObj;
    }

    @Override
    public IPage<User> searchUsers(Integer userId, String userAccount, String userName, String userRole, long current, long size) {
        Page<User> page = new Page<>(current, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userId != null) {
            queryWrapper.eq("userId", userId);
        }
        if (userAccount != null) {
            queryWrapper.eq("userAccount", userAccount);
        }
        if (userName != null) {
            queryWrapper.eq("userName", userName);
        }
        if (userRole != null) {
            queryWrapper.eq("userRole", userRole);
        }
        return this.page(page, queryWrapper);
    }

    @Override
    public User getUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getUserId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public User getUser(User user) {
        return user;
    }

    @Override
    public List<User> getUser(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUser).collect(Collectors.toList());
    }

    @Override
    public UserVO getUserVO(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserVO currentUser = (UserVO) userObj;
        if (currentUser == null || currentUser.getUserId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 3. 用户脱敏
        return currentUser;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public boolean isAdmin(UserVO user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    @Override
    public Integer getCartId(UserVO user) {
        return user.getCartId();
    }

    private void validateUserParams(String userAccount, String userPassword, String userName, String userRole, boolean isLogin) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不能包含特殊字符");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!isLogin) {
            if (StringUtils.isAnyBlank(userName, userRole)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
            }
            if (!userRole.equals(UserRoleEnum.USER.getValue()) && !userRole.equals(UserRoleEnum.PROVIDER.getValue())) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户角色错误");
            }
        }
    }
}




