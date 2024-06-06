package com.software_system_development.arona_mysterious_shop_backend.model.dto.comment;

import lombok.Data;

@Data
public class CommentDeleteRequest {
    /**
     * 评论ID
     */
    private Integer commentId;

    /**
     * 评论对应用户ID
     */
    private Integer commentUserId;
}
