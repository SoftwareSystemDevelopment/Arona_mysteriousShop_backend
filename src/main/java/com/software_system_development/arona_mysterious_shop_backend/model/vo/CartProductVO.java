package com.software_system_development.arona_mysterious_shop_backend.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductVO {

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
     * 商品图片
     */
    private String productImage;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 数量
     */
    private Integer quantity;
}
