package com.software_system_development.arona_mysterious_shop_backend.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {
    private Integer commentId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论创建时间
     */
    private Date commentCreateDate;

    /**
     * 评论对应用户ID
     */
    private Integer commentUserId;

    /**
     * 评论对应商品ID
     */
    private Integer commentProductId;

    /**
     * 评论对应用户昵称
     */
    private String userName;
}
