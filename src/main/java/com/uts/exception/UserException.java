package com.uts.exception;

import com.uts.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class UserException extends Exception {

    private final int code;
    private final ErrorType errorType;  // 新增错误类型

    public UserException(String message, ErrorType errorType) {
        super(message);
        this.code = HttpStatus.BAD_REQUEST.value();  // 默认400
        this.errorType = errorType;
    }

    public UserException(String message, ErrorType errorType, int code) {
        super(message);
        this.code = code;
        this.errorType = errorType;
    }

    public int getCode() {
        return code;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
