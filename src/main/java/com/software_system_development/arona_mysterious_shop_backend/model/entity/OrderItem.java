package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 订单项表
 * @TableName orderItem
 */
@TableName(value ="orderItem")
@Data
public class OrderItem implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单ID，外键，指向order表
     */
    private Integer orderId;

    /**
     * 产品ID，外键，指向product表
     */
    private Integer productId;

    /**
     * 产品数量
     */
    private Integer quantity;

    /**
     * 产品价格
     */
    private BigDecimal price;

    /**
     * 订单项创建时间
     */
    private Date orderItemCreateDate;

    /**
     * 订单项更新时间
     */
    private Date orderItemUpdateDate;

    /**
     * 商品名称
     */
    private String productName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}