package com.software_system_development.arona_mysterious_shop_backend.model.dto.order;

import com.software_system_development.arona_mysterious_shop_backend.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderQueryRequest extends PageRequest implements Serializable {
    /**
     * 订单号
     */
    private String orderCode;

    /**
     *  收货人
     */
    private String receiverName;

    /**
     * 订单状态
     */
    private String orderStatus;

    private static final long serialVersionUID = 1L;
}
