package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName address
 */
@TableName(value ="address")
@Data
public class Address implements Serializable {
    /**
     * 地区ID
     */
    @TableId
    private Integer addressAreaId;

    /**
     * 详细地址信息
     */
    private String addressName;

    /**
     * 创建地址的用户ID
     */
    private Integer addressUserId;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}