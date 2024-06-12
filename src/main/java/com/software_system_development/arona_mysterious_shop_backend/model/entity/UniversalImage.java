package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName universalimage
 */
@TableName(value ="universalimage")
@Data
public class UniversalImage implements Serializable {
    /**
     * 图片ID
     */
    @TableId(type = IdType.AUTO)
    private Integer imageId;

    /**
     * 图片地址
     */
    private String imageSrc;

    /**
     * 图片对应商品ID
     */
    private Integer productId;

    /**
     * 用户id
     */
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}