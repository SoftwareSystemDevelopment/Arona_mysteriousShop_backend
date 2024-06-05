package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName admin
 */
@TableName(value ="admin")
@Data
public class Admin implements Serializable {
    /**
     * 管理员ID
     */
    @TableId(type = IdType.AUTO)
    private Integer adminid;

    /**
     * 管理员名字
     */
    private String adminname;

    /**
     * 管理员账号
     */
    private String adminaccount;

    /**
     * 管理员密码
     */
    private String adminpassword;

    /**
     * 管理员头像
     */
    private String adminavatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}