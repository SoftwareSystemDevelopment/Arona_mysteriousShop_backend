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
    private Integer productid;

    /**
     * 商品名称
     */
    private String productname;

    /**
     * 商品价格
     */
    private BigDecimal productprice;

    /**
     * 商品创建时间
     */
    private Date productcreatedate;

    /**
     * 商品所属分类ID
     */
    private Integer productcategoryid;

    /**
     * 商品是否启用 0-启用 1-未启用
     */
    private Integer productisenabled;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}