package com.software_system_development.arona_mysterious_shop_backend.common;

import lombok.Data;

import java.util.List;

/**
 * 分页请求
 *
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 升序排序字段
     */
    private List<String> ascSortField;

    /**
     * 降序排序字段
     */
    private List<String> descSortField;



}
