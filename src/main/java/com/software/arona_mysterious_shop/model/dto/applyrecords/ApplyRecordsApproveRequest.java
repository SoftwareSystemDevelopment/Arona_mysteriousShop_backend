package com.software.arona_mysterious_shop.model.dto.applyrecords;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName report
 */
@Data
public class ApplyRecordsApproveRequest implements Serializable {


    /**
     * id
     */
    private Long id;


    private Boolean approved;


    private static final long serialVersionUID = 1L;
}