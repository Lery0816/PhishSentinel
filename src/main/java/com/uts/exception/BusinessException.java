package com.uts.exception;


public class BusinessException  extends RuntimeException  {

    private int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
