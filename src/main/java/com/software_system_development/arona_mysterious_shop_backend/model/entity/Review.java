package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName review
 */
@TableName(value ="review")
@Data
public class Review implements Serializable {
    /**
     * 评论ID
     */
    @TableId(type = IdType.AUTO)
    private Integer reviewId;

    /**
     * 评论内容
     */
    private String reviewContent;

    /**
     * 评论创建时间
     */
    private Date reviewCreateDate;

    /**
     * 评论对应用户ID
     */
    private Integer reviewUserId;

    /**
     * 评论对应商品ID
     */
    private Integer reviewProductId;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}