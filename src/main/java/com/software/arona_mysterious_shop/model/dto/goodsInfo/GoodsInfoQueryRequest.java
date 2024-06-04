package com.software.arona_mysterious_shop.model.dto.goodsInfo;

import com.software.arona_mysterious_shop.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;


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


    /**
     * 创建用户 id
     */
    private Long userId;



    private static final long serialVersionUID = 1L;
}