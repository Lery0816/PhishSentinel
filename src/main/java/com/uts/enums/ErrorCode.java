package com.uts.enums;

public enum ErrorCode {
    USER_NOT_LOGIN(1000, "用户未登录"),
    USER_NOT_FOUND(1001, "用户不存在"),
    EMAIL_EXISTS(1002, "邮箱已存在"),
    USERNAME_DUPLICATE(1003, "用户名已存在"),
    DOMAIN_ALREADY_EXISTS(1003, "该域名已存在"),
    DOMAIN_NOT_FOUND(1004, "域名不存在"),
    SYSTEM_ERROR(9999, "系统内部错误");


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}