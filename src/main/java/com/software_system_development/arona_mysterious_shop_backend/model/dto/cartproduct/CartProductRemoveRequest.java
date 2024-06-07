package com.software_system_development.arona_mysterious_shop_backend.model.dto.cartproduct;

import lombok.Data;

@Data
public class CartProductRemoveRequest {
    private int productId;
    private int quantity;
}