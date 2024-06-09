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
     * 订单编号
     */
    @NotNull
    private String orderCode;
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
    /**
     * 下订单的用户ID
     */
    @NotNull
    private Integer orderUserId;

    private static final long serialVersionUID = 1L;
}