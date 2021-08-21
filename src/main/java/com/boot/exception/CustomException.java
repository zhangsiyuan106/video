package com.boot.exception;

import com.boot.enums.ErrorCodeEnum;

public class CustomException extends RuntimeException {

    /**
     * 错误码
     */
    private ErrorCodeEnum errorCodeEnum;

    public CustomException(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }

    public void setErrorCodeEnum(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "errorCodeEnum=" + errorCodeEnum +
                '}';
    }
}
