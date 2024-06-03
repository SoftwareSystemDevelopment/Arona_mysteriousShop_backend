package com.software.arona_mysterious_shop.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 *
 */
@Data
public class UserLoginByWxMpRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 登录场景码
     */
    private String scene;
}
