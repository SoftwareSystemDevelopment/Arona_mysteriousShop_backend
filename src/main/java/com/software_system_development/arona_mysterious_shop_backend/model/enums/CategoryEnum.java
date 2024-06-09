package com.software_system_development.arona_mysterious_shop_backend.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "商品类型")
public enum CategoryEnum {
    @Schema(description = "报告")
    REPORT("报告", "report"),
    @Schema(description = "强化石")
    EXP_ORB("强化石", "exp_orb"),
    @Schema(description = "专武材料")
    UE_EXP_MATERIAL("专武材料", "ue_exp_material"),
    @Schema(description = "技能光盘")
    BD("技能光盘", "bd"),
    @Schema(description = "技能书")
    SKILL_BOOK("技能书", "skill_book"),
    @Schema(description = "欧帕兹")
    OOPARTS("欧帕兹", "ooparts");

    private final String text;

    private final String value;

    CategoryEnum(String text, String value) {
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
     * @return {@link CategoryEnum}
     */
    public static CategoryEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (CategoryEnum anEnum : CategoryEnum.values()) {
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
