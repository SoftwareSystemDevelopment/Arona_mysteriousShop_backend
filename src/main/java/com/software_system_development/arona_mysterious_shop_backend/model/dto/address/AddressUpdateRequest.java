package com.software_system_development.arona_mysterious_shop_backend.model.dto.address;

import lombok.Data;

@Data
public class AddressUpdateRequest {
    /**
     * 地址ID
     */
    private Integer addressId;

    /**
     * 地址
     */
    private String addressName;

    /**
     * 收件人姓名
     */
    private String receiver;

    /**
     * 收件人手机号
     */
    private String userPhone;
}
