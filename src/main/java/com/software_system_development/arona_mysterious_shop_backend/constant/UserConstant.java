package com.software_system_development.arona_mysterious_shop_backend.constant;

/**
 * 用户常量
 *
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    /**
     * 系统用户 id
     *  todo 根据实际修改
     */
    long SYSTEM_USER_ID = 1L;

    /**
     * 默认权限
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员权限
     */
    String ADMIN_ROLE = "admin";

    /**
     * 供货商
     */
    String PROVIDER_ROLE = "provider";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";
}
