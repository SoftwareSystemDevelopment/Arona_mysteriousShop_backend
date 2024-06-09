package com.software_system_development.arona_mysterious_shop_backend.model.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 *
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户账号
     */
    @NotNull
    private String userAccount;

    /**
     * 用户密码
     */
    @NotNull
    private String userPassword;

}
