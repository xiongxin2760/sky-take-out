package com.sky.exception;

/**
 * 业务异常
 */
public class BaseException extends RuntimeException {
    private Integer code = 500;

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Integer code) {
        super(msg);
        this.code = code;
    }
}
