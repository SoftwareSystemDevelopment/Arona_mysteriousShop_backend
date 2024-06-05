package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

/**
 * 
 * @TableName product
 */
@TableName(value ="product")
@Data
public class Product implements Serializable {
    /**
     * 商品ID
     */
    @TableId(type = IdType.AUTO)
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品创建时间
     */
    private Date productCreateDate;

    /**
     * 商品所属分类
     */
    private String productCategoryName;

    /**
     * 商品是否启用 0-启用 1-未启用
     */
    private Integer productIsEnabled;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 供货商ID
     */
    private Long providerId;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}