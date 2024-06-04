package com.software.arona_mysterious_shop.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单状态枚举
 *
 */
public enum OrderStatusEnum {


    NOT_APPLIED( "未下单",0),
    PENDING("审核中",1),
    APPROVED("下单成功",2),
    REJECTED( "下单失败",3);


    private final String text;

    private final int value;

    OrderStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return {@link List}<{@link Integer}>
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 值
     * @return {@link OrderStatusEnum}
     */
    public static OrderStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (OrderStatusEnum productStatusEnum : OrderStatusEnum.values()) {
            if (productStatusEnum.getValue() == value) {
                return productStatusEnum;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
