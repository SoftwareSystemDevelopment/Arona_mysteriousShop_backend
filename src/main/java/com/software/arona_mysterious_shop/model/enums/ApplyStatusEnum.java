package com.software.arona_mysterious_shop.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单状态枚举
 *
 */
public enum ApplyStatusEnum {


    NOT_APPLIED( "未下单",0),
    PENDING("处理中",1),
    APPROVED("下单成功",2),
    REJECTED( "下单失败",3);


    private final String text;

    private final int value;

    ApplyStatusEnum(String text, int value) {
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
     * @return {@link ApplyStatusEnum}
     */
    public static ApplyStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ApplyStatusEnum productStatusEnum : ApplyStatusEnum.values()) {
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
