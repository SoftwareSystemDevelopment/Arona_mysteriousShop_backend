package com.software_system_development.arona_mysterious_shop_backend.model.vo;

import com.software_system_development.arona_mysterious_shop_backend.model.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderVO {
    private String orderCode;
    private String orderAddress;
    private String orderReceiver;
    private String orderMobile;
    private Date orderPayDate;
    private int orderStatus;
    private List<OrderItem> orderItems;

}