package com.software_system_development.arona_mysterious_shop_backend.model.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName report
 */
@Data
public class OrderAddRequest implements Serializable {

    /**
     * 收货地址
     */
    @NotNull
    private Integer orderAddress;

    private static final long serialVersionUID = 1L;
}