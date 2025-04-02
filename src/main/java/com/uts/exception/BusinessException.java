package com.uts.exception;


public class BusinessException  extends RuntimeException  {

    private int code;// 新增错误类型

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
