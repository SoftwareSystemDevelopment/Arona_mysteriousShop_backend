package com.software_system_development.arona_mysterious_shop_backend.model.dto.orderinfo;

import lombok.Data;

@Data
public class OrderApproveRequest {
    /**
     * 主键
     */
    private Long id;

    /**
     * 订购状态（0：未下单，1：审核中，2：下单成功，3：下单失败）
     */
    private Integer status;

    /**
     * 是否通过审核， 0：不通过， 1： 通过
     */
    private Integer isApproved;
}
