package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
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
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户地址id
     */
    private String userAddress;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色 user/admin/ban
     */
    private String userRole;

    /**
     * 是否删除
     */
    @TableLogic
    private int isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}