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
 * @TableName cart_item
 */
@TableName(value ="cart_item")
@Data
public class CartItem implements Serializable {
    /**
     * 购物车项ID
     */
    @TableId(type = IdType.AUTO)
    private Integer cartItemId;

    /**
     * 购物车ID
     */
    private Integer cartId;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 价格
     */
    private BigDecimal price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}