package com.software_system_development.arona_mysterious_shop_backend.model.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新个人信息请求
 *
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * 用户id
     */
    @NotNull
    private Integer userId;

    /**
     * 用户昵称
     */
    @NotNull
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    private static final long serialVersionUID = 1L;
}