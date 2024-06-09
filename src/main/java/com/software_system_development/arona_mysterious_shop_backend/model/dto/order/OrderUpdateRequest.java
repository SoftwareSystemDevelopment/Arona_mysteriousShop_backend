package com.software_system_development.arona_mysterious_shop_backend.model.dto.order;

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
     * 收货地址
     */
    @NotNull
    private Integer orderAddress;
    /**
     * 收货人
     */
    @NotNull
    private String orderReceiver;
    /**
     * 收货人手机号
     */
    @NotNull
    private String orderMobile;

    private static final long serialVersionUID = 1L;
}