package com.github.trang.dynamic.base.response;

import com.github.trang.dynamic.domain.enums.EnumRst;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回值封装
 *
 * @author trang
 */
@Getter
@ToString
public class Message<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    private int code;
    /** 状态描述 */
    private String msg;
    /** 实际返回的数据 */
    private T data;

    private Message(Rst rst) {
        this(rst, null, null);
    }

    private Message(Rst rst, String msg) {
        this(rst, msg, null);
    }

    private Message(Rst rst, T data) {
        this(rst, null, data);
    }

    private Message(Rst rst, String msg, T data) {
        this.code = rst.getCode();
        this.msg = rst.getDesc();
        if (msg != null && !msg.isEmpty()) {
            this.msg = msg;
        }
        if (data != null) {
            this.data = data;
        }
    }

    public static <T> Message<T> ok() {
        return new Message<>(EnumRst.OK);
    }

    public static <T> Message<T> ok(T data) {
        return new Message<>(EnumRst.OK, data);
    }

    public static <T> Message<T> error(Rst rst) {
        return new Message<>(rst);
    }

    public static <T> Message<T> error(Rst rst, String msg) {
        return new Message<>(rst, msg);
    }

}