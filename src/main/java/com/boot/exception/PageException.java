package com.boot.exception;

import com.boot.constant.Status;

/**
 * @Author: zsy
 * @Description 页面异常处理
 * @Date: Create in 20:34 2021/4/29
 */
public class PageException extends  BaseException{

    public PageException(Status status) {
        super(status);
    }

    public PageException(Integer code, String message) {
        super(code, message);
    }
}
