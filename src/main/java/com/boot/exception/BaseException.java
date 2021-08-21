package com.boot.exception;

import com.boot.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: zsy
 * @Description
 * @Date: Create in 20:30 2021/4/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException{
    private Integer code;
    private String message;
    public BaseException(Status status){
        super(status.getMessage());
        this.code=status.getCode();
        this.message=status.getMessage();
    }
    public BaseException(Integer code,String message){
        super(message);
        this.code=code;
        this.message=message;
    }
}
