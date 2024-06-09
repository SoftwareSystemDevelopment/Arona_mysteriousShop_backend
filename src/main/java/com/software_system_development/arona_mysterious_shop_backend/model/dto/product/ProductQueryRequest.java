package com.software_system_development.arona_mysterious_shop_backend.model.dto.product;

import com.software_system_development.arona_mysterious_shop_backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductQueryRequest extends PageRequest implements Serializable {

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品所属分类
     */
    private String productCategoryName;

    /**
     * 最低价格
     */
    private BigDecimal minPrice;

    /**
     * 最高价格
     */
    private BigDecimal maxPrice;

    /**
     * 商品描述
     */
    private String productDescription;


    private static final long serialVersionUID = 1L;
}