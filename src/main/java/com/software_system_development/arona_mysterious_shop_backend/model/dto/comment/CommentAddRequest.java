package com.software_system_development.arona_mysterious_shop_backend.model.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentAddRequest {
    /**
     * 评论内容
     */
    @NotNull
    private String commentContent;

    /**
     * 评论对应商品ID
     */
    @NotNull
    private Integer commentProductId;
}
