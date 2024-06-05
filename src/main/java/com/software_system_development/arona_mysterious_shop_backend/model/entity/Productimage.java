package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName productimage
 */
@TableName(value ="productimage")
@Data
public class Productimage implements Serializable {
    /**
     * 商品图片ID
     */
    @TableId(type = IdType.AUTO)
    private Integer productimageid;

    /**
     * 商品图片类型 0-封面 1-详情 2-轮播
     */
    private Integer productimagetype;

    /**
     * 商品图片地址
     */
    private String productimagesrc;

    /**
     * 图片对应商品ID
     */
    private Integer productimageproductid;

    /**
     * 图片存储位置
     */
    private String productimagestore;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}