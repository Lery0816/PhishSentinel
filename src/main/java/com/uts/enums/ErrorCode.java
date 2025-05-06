package com.uts.enums;

public enum ErrorCode {
    USER_NOT_LOGIN(1000, "User not logged in"),
    USER_NOT_FOUND(1001, "The user does not exist"),
    EMAIL_EXISTS(1002, "Email already exists"),
    USERNAME_DUPLICATE(1003, "The username already exists"),
    DOMAIN_ALREADY_EXISTS(1003, "The domain name already exists"),
    DOMAIN_NOT_FOUND(1004, "Domain name does not exist"),
    SYSTEM_ERROR(9999, "Internal system error");


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