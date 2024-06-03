package com.software.arona_mysterious_shop.model.dto.applyrecords;

import com.software.arona_mysterious_shop.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplyRecordsQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 关联到产品信息表中的 id 字段，表示订购的是哪个产品
     */
    private Long goodsId;

    /**
     * 产品名称
     */
    private String goodsName;

    /**
     * 关联到用户表中的 id 字段，表示订购者的用户ID
     */
    private Long applicantId;

    /**
     * 关联到用户表中的 userName 字段，表示订购者的用户名
     */
    private String applicantUserName;

    /**
     * 记录申请的时间
     */
    private Date applicationTime;

    /**
     * 订购状态（0：未下单，1：下单中，2：下单成功，3：下单失败）
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}