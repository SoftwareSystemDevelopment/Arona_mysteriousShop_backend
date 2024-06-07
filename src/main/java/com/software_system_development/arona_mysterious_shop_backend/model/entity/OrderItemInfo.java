package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemInfo {
    private int productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
}

