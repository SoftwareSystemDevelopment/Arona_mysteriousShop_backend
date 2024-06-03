package com.software.arona_mysterious_shop.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单记录表
 * @TableName apply_records
 */
@TableName(value ="apply_records")
@Data
public class ApplyRecords implements Serializable {
    /**
     * 主键，用于唯一标识每条订单记录
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联到产品信息表中的 id 字段，表示订购的是哪个产品
     */
    private Long goodsId;

    /**
     * 产品名称
     */
    private String goodsName;

    /**
     * 关联到用户表中的 id 字段，表示订购者的用户ID
     */
    private Long applicantId;

    /**
     * 关联到用户表中的 userName 字段，表示订购者的用户名
     */
    private String applicantUserName;

    /**
     * 记录订购的时间
     */
    private Date applicationTime;

    /**
     * 订购状态（0：未下单，1：下单中，2：下单成功，3：下单失败）
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}