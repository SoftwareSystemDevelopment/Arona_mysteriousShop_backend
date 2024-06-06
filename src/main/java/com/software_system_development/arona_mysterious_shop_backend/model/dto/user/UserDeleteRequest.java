package com.software_system_development.arona_mysterious_shop_backend.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 */
@Data
public class UserDeleteRequest implements Serializable {

    private Integer userId;

    private static final long serialVersionUID = 1L;
}