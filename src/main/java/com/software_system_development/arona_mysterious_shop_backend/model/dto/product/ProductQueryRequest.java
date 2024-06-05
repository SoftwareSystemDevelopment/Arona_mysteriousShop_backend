package com.software_system_development.arona_mysterious_shop_backend.model.dto.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.software_system_development.arona_mysterious_shop_backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductQueryRequest extends PageRequest implements Serializable {

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

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