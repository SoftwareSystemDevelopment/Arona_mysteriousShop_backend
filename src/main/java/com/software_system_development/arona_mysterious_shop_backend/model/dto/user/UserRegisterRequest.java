package com.software_system_development.arona_mysterious_shop_backend.model.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户账号
     */
    @NotNull
    private String userAccount;

    /**
     * 用户昵称
     */
    @NotNull
    private String userName;

    /**
     * 用户密码
     */
    @NotNull
    private String userPassword;

    /**
     * 用户角色
     */
    @NotNull
    private String userRole;

}
