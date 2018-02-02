package com.github.trang.dynamic.exception;

import com.github.trang.dynamic.base.response.Rst;
import lombok.Getter;

/**
 * 业务封装异常
 *
 * @author trang
 */
@Getter
public class DynamicException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected Rst status;
    protected String msg;

    public DynamicException() {
        super();
    }

    public DynamicException(Rst status) {
        super(status.getDesc());
        this.status = status;
        this.msg = status.getDesc();
    }

    public DynamicException(Rst status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }

    public DynamicException(Rst status, boolean stackTrace) {
        super(status.getDesc(), null, true, false);
        this.status = status;
        this.msg = status.getDesc();
    }

    public DynamicException(Rst status, String msg, boolean stackTrace) {
        super(msg, null, true, false);
        this.status = status;
        this.msg = msg;
    }

}