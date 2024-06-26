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
 * @TableName order_
 */
@TableName(value ="order_")
@Data
public class Order implements Serializable {
    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    /**
     * 订单对应地址ID
     */
    private Integer orderAddress;

    /**
     * 下单时间
     */
    private Date orderPayDate;

    /**
     * 订单状态 0-待支付 1-已下单 2-待收货 3-已收货 4-已取消
     */
    private Integer orderStatus;

    /**
     * 订单对应用户ID
     */
    private Integer orderUserId;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}