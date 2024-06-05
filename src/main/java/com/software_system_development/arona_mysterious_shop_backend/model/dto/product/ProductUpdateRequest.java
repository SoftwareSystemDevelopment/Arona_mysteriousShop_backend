package com.software_system_development.arona_mysterious_shop_backend.model.dto.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 更新请求
 *
 * @TableName report
 */
@Data
public class ProductUpdateRequest implements Serializable {

    /**
     * id
     */
    private Integer productId;

    /**
     * 名称
     */
    private String productName;

    /**
     * 价格
     */
    private BigDecimal productPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 商品所属分类
     */
    private String productCategoryName;

    /**
     * 商品是否启用 0-启用 1-未启用
     */
    private Integer productIsEnabled;

    /**
     * 供货商ID
     */
    private Long providerId;

    private static final long serialVersionUID = 1L;
}