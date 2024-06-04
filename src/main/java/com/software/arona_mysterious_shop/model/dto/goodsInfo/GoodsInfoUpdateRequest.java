package com.software.arona_mysterious_shop.model.dto.goodsInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新请求
 *
 * @TableName report
 */
@Data
public class GoodsInfoUpdateRequest implements Serializable {

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

    /**
     * 是否公开浏览，0: 关闭，1: 启用，默认关闭
     */
    private Integer status;


    private static final long serialVersionUID = 1L;
}