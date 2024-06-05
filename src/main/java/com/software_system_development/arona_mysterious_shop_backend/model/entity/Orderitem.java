package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName orderitem
 */
@TableName(value ="orderitem")
@Data
public class Orderitem implements Serializable {
    /**
     * 订单项ID
     */
    @TableId(type = IdType.AUTO)
    private Integer orderitemid;

    /**
     * 商品项数量
     */
    private Integer orderitemnumber;

    /**
     * 商品项价格
     */
    private BigDecimal orderitemprice;

    /**
     * 商品项对应商品ID
     */
    private Integer orderitemproductid;

    /**
     * 订单项对应订单ID
     */
    private Integer orderitemorderid;

    /**
     * 订单项对应用户ID
     */
    private Integer orderitemuserid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}