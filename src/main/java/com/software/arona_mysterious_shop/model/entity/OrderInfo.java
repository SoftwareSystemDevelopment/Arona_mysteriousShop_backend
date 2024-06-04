package com.software.arona_mysterious_shop.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单表
 * @TableName order_info
 */
@TableName(value ="order_info")
@Data
public class OrderInfo implements Serializable {
    /**
     * 主键，用于唯一标识每条申请记录
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联到用户表中的 id 字段，表示购买者的用户ID
     */
    private Long userId;

    /**
     * 关联到用户表中的 userName 字段，表示购买者的用户名
     */
    private String userName;

    /**
     * 订单下达的时间
     */
    private Date orderTime;

    /**
     * 申请状态（0：未申请，1：审核中，2：通过审核，3：审核不通过）
     */
    private Integer status;

    /**
     * 是否删除，0: 未删除，1: 已删除，默认未删除
     */
    private Integer isDelete;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 记录最后更新时间
     */
    private Date updateTime;

    /**
     * 订单总金额
     */
    private Long totalAmount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}