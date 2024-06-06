package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * 评论ID
     */
    @TableId(type = IdType.AUTO)
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
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}