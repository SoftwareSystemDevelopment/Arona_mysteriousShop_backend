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
     * 商品所属分类名称
     */
    private String productCategoryName;

    /**
     * 商品是否启用 0-启用 1-未启用
     */
    private Integer productIsEnabled;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 供应商Id
     */
    private Integer providerId;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品创建时间
     */
    private Date productCreateDate;

    /**
     * 商品更新时间
     */
    private Date productUpdateDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}