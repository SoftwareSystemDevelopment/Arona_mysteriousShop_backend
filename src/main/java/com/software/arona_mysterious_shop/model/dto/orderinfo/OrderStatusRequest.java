package com.software.arona_mysterious_shop.model.dto.orderinfo;


import lombok.Data;

import java.util.List;

@Data
public class OrderStatusRequest {
    private List<Long> ids;
    private static final long serialVersionUID = 1L;
}
