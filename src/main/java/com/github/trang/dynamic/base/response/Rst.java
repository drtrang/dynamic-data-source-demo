package com.github.trang.dynamic.base.response;

/**
 * 响应状态码
 *
 * @author trang
 * @see Message
 */
public interface Rst {

    /**
     * 状态码
     *
     * @return code
     */
    int getCode();

    /**
     * 状态枚举
     *
     * @return msg
     */
    String getMsg();

    /**
     * 状态描述
     *
     * @return desc
     */
    String getDesc();

}