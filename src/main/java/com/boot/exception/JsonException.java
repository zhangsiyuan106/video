package com.boot.exception;

import com.boot.constant.Status;

/**
 * @Author: zsy
 * @Description json异常处理
 * @Date: Create in 20:33 2021/4/29
 */
public class JsonException extends BaseException{
    public JsonException(Status status) {
        super(status);
    }

    public JsonException(Integer code, String message) {
        super(code, message);
    }
}
