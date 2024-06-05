package com.software_system_development.arona_mysterious_shop_backend.model.dto.orderinfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建请求
 *
 * @TableName report
 */
@Data
public class OrderAddRequest implements Serializable {

    /**
     * 关联到用户表中的 id 字段，表示订购者的用户ID
     */
    private Long userId;

    /**
     * 关联到用户表中的 userName 字段，表示订购者的用户名
     */
    private String userName;

    /**
     * 订购状态（0：未下单，1：审核中，2：下单成功，3：下单失败）
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}