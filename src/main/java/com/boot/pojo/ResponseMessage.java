package com.boot.pojo;

public class ResponseMessage {

    public ResponseMessage(Integer userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    /**
     * 响应消息
     */
    private String message ;
    private Integer userId ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
