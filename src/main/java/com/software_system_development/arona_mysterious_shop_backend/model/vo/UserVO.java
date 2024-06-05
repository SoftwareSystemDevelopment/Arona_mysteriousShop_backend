package com.software_system_development.arona_mysterious_shop_backend.model.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户视图（脱敏）
 *
 */
@Data
public class UserVO implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色
     */
    private String userRole;

    /**
     * 用户地址
     */
    private String userAddress;

    private static final long serialVersionUID = 1L;
}