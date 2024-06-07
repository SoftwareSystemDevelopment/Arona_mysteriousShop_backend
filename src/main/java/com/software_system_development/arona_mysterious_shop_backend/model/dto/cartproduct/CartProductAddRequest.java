package com.software_system_development.arona_mysterious_shop_backend.model.dto.cartproduct;

import lombok.Data;

@Data
public class CartProductAddRequest {
    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 数量
     */
    private Integer quantity;
}
