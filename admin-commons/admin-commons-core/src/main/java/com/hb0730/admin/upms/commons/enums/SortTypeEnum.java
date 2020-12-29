package com.hb0730.admin.upms.commons.enums;

/**
 * 排序枚举
 *
 * @author bing_huang
 */
public enum SortTypeEnum implements ValueEnum<String> {
    /**
     * 降序
     */
    DESC("DESC"),
    /**
     * 升序
     */
    ASC("ASC");

    private final String value;

    SortTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
