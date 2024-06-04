package com.software.arona_mysterious_shop.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GoodsTypeEnum {
    REPORT("报告", "report"),

    EXP_ORB("强化石", "exp_orb"),

    UE_EXP_MATERIAL("专武材料", "ue_exp_material"),

    BD("技能光盘", "bd"),

    SKILL_BOOK("技能书", "skill_book"),

    OOPARTS("欧帕兹", "ooparts");

    private final String text;

    private final String value;

    GoodsTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return {@link List}<{@link String}>
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 值
     * @return {@link GoodsTypeEnum}
     */
    public static GoodsTypeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (GoodsTypeEnum anEnum : GoodsTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public static boolean contains(String type) {
        return Arrays.stream(values()).anyMatch(item -> item.value.equals(type));
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
