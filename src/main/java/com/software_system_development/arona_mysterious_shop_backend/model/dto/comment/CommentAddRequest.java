package com.software_system_development.arona_mysterious_shop_backend.model.dto.comment;

import lombok.Data;

@Data
public class CommentAddRequest {
    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论对应商品ID
     */
    private Integer commentProductId;
}
