package com.software_system_development.arona_mysterious_shop_backend.model.dto.order;

import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新请求
 *
 * @TableName report
 */
@Data
public class OrderUpdateRequest implements Serializable {

    /**
     * 订单id
     */
    @NotNull
    private Integer orderId;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    private static final long serialVersionUID = 1L;
}