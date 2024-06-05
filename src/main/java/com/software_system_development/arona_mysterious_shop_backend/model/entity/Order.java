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
 * @TableName order
 */
@TableName(value ="order")
@Data
public class Order implements Serializable {
    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer orderid;

    /**
     * 订单编号
     */
    private String ordercode;

    /**
     * 订单对应地址ID
     */
    private String orderaddress;

    /**
     * 订单详细地址
     */
    private String orderdetailaddress;

    /**
     * 收件人
     */
    private String orderreceiver;

    /**
     * 收件人手机号
     */
    private String ordermobile;

    /**
     * 支付时间
     */
    private Date orderpaydate;

    /**
     * 发货时间
     */
    private Date orderdeliverydate;

    /**
     * 确认收货时间
     */
    private Date orderconfirmdate;

    /**
     * 订单状态 0-待支付 1-待发货 2-待收货 3-已收货 4-已取消
     */
    private Integer orderstatus;

    /**
     * 订单对应用户ID
     */
    private Integer orderuserid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}