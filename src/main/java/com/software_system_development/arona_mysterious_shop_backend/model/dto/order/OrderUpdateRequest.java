package com.software_system_development.arona_mysterious_shop_backend.model.dto.order;

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

    private Integer orderId;
    private Integer orderAddress;
    private String orderReceiver;
    private String orderMobile;

    private static final long serialVersionUID = 1L;
}