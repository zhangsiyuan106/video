package com.boot.constant;

import lombok.Getter;

/**
 * @Author: zsy
 * @Description
 * @Date: Create in 20:27 2021/4/29
 */
@Getter
public enum  Status {
    /**
     * 操作成功
     */
    OK(200,"操作成功"),
    /**
     * 未知异常
     */
    UNKNOWN_ERROR(500, "服务器出错啦");
    private Integer code;

    private String message;

    Status(Integer code,String message){
        this.code=code;
        this.message=message;
    }

}
