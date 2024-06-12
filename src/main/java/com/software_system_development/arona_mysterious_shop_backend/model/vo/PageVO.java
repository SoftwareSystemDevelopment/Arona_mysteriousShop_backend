package com.software_system_development.arona_mysterious_shop_backend.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    private List<T> records;
    private long total;

    public PageVO(List<T> records, long total) {
        this.records = records;
        this.total = total;
    }
}