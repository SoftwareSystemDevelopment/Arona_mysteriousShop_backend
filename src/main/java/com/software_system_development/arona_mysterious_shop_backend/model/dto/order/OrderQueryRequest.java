package com.software_system_development.arona_mysterious_shop_backend.model.dto.order;

import com.software_system_development.arona_mysterious_shop_backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 关联到用户表中的 id 字段，表示订购者的用户ID
     */
    private Long userId;

    /**
     * 关联到用户表中的 userName 字段，表示订购者的用户名
     */
    private String userName;

    /**
     * 订购状态（0：未下单，1：下单中，2：下单成功，3：下单失败）
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}