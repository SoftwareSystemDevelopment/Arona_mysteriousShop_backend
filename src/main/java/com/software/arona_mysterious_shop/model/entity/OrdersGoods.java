package com.software.arona_mysterious_shop.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 订单商品关联表
 * @TableName orders_goods
 */
@TableName(value ="orders_goods")
@Data
public class OrdersGoods implements Serializable {
    /**
     * 订单ID
     */
    @TableId
    private Long orderId;

    /**
     * 商品ID
     */
    @TableId
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品单价（下单时的价格，分）
     */
    private Integer price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}