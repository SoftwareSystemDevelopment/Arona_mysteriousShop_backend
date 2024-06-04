package com.software.arona_mysterious_shop.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品视图（脱敏）
 */
@Data
public class GoodsInfoVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片
     */
    private String cover;

    /**
     * 价格（分）
     */
    private Integer price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 产品类型
     */
    private String types;


    private static final long serialVersionUID = 1L;
}