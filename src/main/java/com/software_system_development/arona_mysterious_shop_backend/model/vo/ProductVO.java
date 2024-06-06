package com.software_system_development.arona_mysterious_shop_backend.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品视图（脱敏）
 */
@Data
public class ProductVO implements Serializable {
    /**
     * 商品ID
     */
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
     * 商品所属分类
     */
    private String productCategoryName;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 供货商ID
     */
    private Integer providerId;

    /**
     * 商品创建时间
     */
    private Date productCreateDate;

    private static final long serialVersionUID = 1L;
}