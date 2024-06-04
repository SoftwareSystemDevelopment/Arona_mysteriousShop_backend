package com.software.arona_mysterious_shop.model.dto.orderinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName report
 */
@Data
public class OrderApplyRequest implements Serializable {


    /**
     * 关联到产品信息表中的 id 字段，表示订购的是哪个产品
     */
    private Long goodsId;

    /**
     * 订购数量
     */
    private Integer applyNums;


    private static final long serialVersionUID = 1L;
}