package com.software_system_development.arona_mysterious_shop_backend.model.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName report
 */
@Data
public class OrderAddRequest implements Serializable {

    private String orderCode;
    private Integer orderAddress;
    private String orderReceiver;
    private String orderMobile;
    private Integer orderUserId;

    private static final long serialVersionUID = 1L;
}