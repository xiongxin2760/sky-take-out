package com.sky.exception;

public class BussinessException extends BaseException {
    public BussinessException() {
    }

    public BussinessException(String msg) {
        super(msg);
    }

    public BussinessException(String msg, Integer code) {
        super(msg, code);
    }
}
