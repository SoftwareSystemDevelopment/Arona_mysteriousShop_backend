package com.software_system_development.arona_mysterious_shop_backend.model.dto.address;

import lombok.Data;

@Data
public class AddressDeleteRequest {
    private Integer addressAreaId;
    private Integer userId;
}
