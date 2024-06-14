package com.software_system_development.arona_mysterious_shop_backend.model.vo;

import com.software_system_development.arona_mysterious_shop_backend.model.entity.OrderItem;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderVO {
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 收货地址
     */
    private String orderAddress;
    /**
     * 收货人
     */
    private String orderReceiver;
    /**
     * 收货人手机号
     */
    private String orderMobile;
    /**
     * 下单日期
     */
    private Date orderPayDate;
    /**
     * 订单状态
     */
    private int orderStatus;
    /**
     * 订单内容
     */
    private List<OrderItem> orderItems;

}