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
 * @TableName review
 */
@TableName(value ="review")
@Data
public class Review implements Serializable {
    /**
     * 评论ID
     */
    @TableId(type = IdType.AUTO)
    private Integer reviewid;

    /**
     * 评论内容
     */
    private String reviewcontent;

    /**
     * 评论创建时间
     */
    private Date reviewcreatedate;

    /**
     * 评论对应用户ID
     */
    private Integer reviewuserid;

    /**
     * 评论对应商品ID
     */
    private Integer reviewproductid;

    /**
     * 评论对应订单项ID
     */
    private Integer revieworderitemid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}