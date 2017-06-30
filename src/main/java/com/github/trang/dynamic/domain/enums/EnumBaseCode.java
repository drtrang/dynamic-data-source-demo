package com.github.trang.dynamic.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础数据表，命名为表名_字段名，code 为字段名
 *
 * @author trang
 */
@AllArgsConstructor
@ToString
public enum EnumBaseCode {
    // 2017-02-06 10:00:00,2017-02-07 09:00:00
    DEADLINE_MODIFY_DATETIME("deadline_modify_datetime", "修改数据时间"),
    DROP_REASON("drop_reason", "剔除原因"),
    NOT_TARGET_REASON("not_target_reason", "非目标原因");

    private static Map<String, EnumBaseCode> MAP = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(type -> MAP.put(type.getCode(), type));
    }

    // basecode 值，对应 code_type 字段
    @Getter
    private String code;
    // basecode 描述，对应 remark 字段
    @Getter
    private String desc;

    public static EnumBaseCode getByCode(String code) {
        return MAP.get(code);
    }
}
