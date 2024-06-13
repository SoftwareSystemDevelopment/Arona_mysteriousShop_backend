package com.software_system_development.arona_mysterious_shop_backend.model.vo;

import lombok.Data;

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
     * 数量
     */
    private Integer quantity;
}
