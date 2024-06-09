package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName productImage
 */
@TableName(value ="productImage")
@Data
public class ProductImage implements Serializable {
    /**
     * 商品图片ID
     */
    @TableId(type = IdType.AUTO)
    private Integer productImageId;

    /**
     * 商品图片地址
     */
    private String productImageSrc;

    /**
     * 图片对应商品ID
     */
    private Integer productImageProductId;

    private static final long serialVersionUID = 1L;
}