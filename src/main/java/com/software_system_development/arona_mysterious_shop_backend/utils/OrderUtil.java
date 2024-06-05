package com.software_system_development.arona_mysterious_shop_backend.utils;

public final class OrderUtil {
    //排序字段
    private String orderBy;
    //倒序排序
    private boolean isDesc;

    public OrderUtil(String orderBy){
        this.orderBy = orderBy;
        this.isDesc = false;
    }

    public OrderUtil(String orderBy,boolean isDesc) {
        this.orderBy = orderBy;
        this.isDesc = isDesc;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public boolean getIsDesc() {
        return isDesc;
    }
}
