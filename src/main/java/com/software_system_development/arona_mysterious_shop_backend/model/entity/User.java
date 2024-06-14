package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
    private Integer userId;

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
     * 用户角色 user/admin/provider
     */
    private String userRole;

    /**
     * 是否删除 0-正常 1-删除
     */
    private Integer isDelete;

    /**
     * 用户创建时间
     */
    private Date userCreateDate;

    /**
     * 用户最近更新时间
     */
    private Date userUpdateDate;

    /**
     * 用户对应的购物车id
     */
    private Integer cartId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}