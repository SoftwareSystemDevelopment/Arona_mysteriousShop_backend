package com.software_system_development.arona_mysterious_shop_backend.model.dto.product;

import lombok.Data;

@Data
public class ProductDeleteRequest {
    /**
     * id
     */
    private Integer productId;

    /**
     * 供货商ID
     */
    private Long providerId;
}
