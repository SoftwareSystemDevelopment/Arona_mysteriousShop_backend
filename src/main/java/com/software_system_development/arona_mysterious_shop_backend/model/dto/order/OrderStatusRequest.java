package com.software_system_development.arona_mysterious_shop_backend.model.dto.order;


import lombok.Data;

import java.util.List;

@Data
public class OrderStatusRequest {
    private List<Long> ids;
    private static final long serialVersionUID = 1L;
}
