package com.software_system_development.arona_mysterious_shop_backend.model.dto.product;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Integer productId;

    /**
     * 名称
     */
    @NotNull
    private String productName;

    /**
     * 价格
     */
    @NotNull
    private BigDecimal productPrice;

    /**
     * 库存
     */
    @NotNull
    private Integer stock;

    /**
     * 商品所属分类
     */
    @NotNull
    private String productCategoryName;

    /**
     * 商品是否启用 0-启用 1-未启用
     */
    @NotNull
    private Integer productIsEnabled;

    /**
     * 商品描述
     */
    @NotNull
    private String productDescription;

    private static final long serialVersionUID = 1L;
}