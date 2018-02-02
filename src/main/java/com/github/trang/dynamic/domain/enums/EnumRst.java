package com.github.trang.dynamic.domain.enums;

import com.github.trang.dynamic.base.response.Rst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import one.util.streamex.StreamEx;

import java.util.HashMap;
import java.util.Map;

/**
 * 外部系统返回值
 *
 * @author trang
 */
@AllArgsConstructor
@Getter
public enum EnumRst implements Rst {

    OK(1, "OK", "恭喜您，操作成功"),
    BAD_REQUEST(400, "Bad Request", "请求异常"),
    FORBIDDEN(403, "Forbidden", "禁止访问"),
    NOT_FOUND(404, "Not Found", "没有找到资源"),
    REQUEST_TIMEOUT(408, "Request Timeout", "请求超时"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "服务内部错误"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable", "服务不可用");

    private int code;
    private String msg;
    private String desc;

    private static Map<Integer, EnumRst> map = new HashMap<>();
    static {
        StreamEx.of(values()).forEach(type -> map.put(type.getCode(), type));
    }

    public static EnumRst getByCode(int code) {
        return map.get(code);
    }

}