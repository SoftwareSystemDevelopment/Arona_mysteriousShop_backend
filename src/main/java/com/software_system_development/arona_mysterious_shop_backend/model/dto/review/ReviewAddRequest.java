package com.software_system_development.arona_mysterious_shop_backend.model.dto.review;

import lombok.Data;

@Data
public class ReviewAddRequest {
    private String reviewContent;
    private Integer reviewUserId;
    private Integer reviewProductId;
}
