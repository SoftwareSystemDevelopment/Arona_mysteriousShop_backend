package com.software_system_development.arona_mysterious_shop_backend.model.dto.shop;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopUpdateRequest {

    /**
     * 店铺ID
     */
    @NotNull
    private Integer shopId;

    /**
     * 店铺名称
     */
    @NotNull
    private String name;

    /**
     * 店铺描述
     */
    @NotNull
    private String description;
}

