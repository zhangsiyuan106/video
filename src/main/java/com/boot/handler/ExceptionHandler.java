package com.boot.handler;

import com.boot.constant.ApiResponse;
import com.boot.exception.JsonException;
import com.boot.exception.PageException;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: zsy
 * @Description
 * @Date: Create in 20:44 2021/4/29
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {
    private static final String DEAFULT_ERROR_VIEW = "error";

    /**
     * 统一 json 异常处理
     * @param exception JsonException
     * @return 统一返回Json格式
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = JsonException.class)
    @ResponseBody
    public ApiResponse jsonErrorHandler(JsonException exception){
        log.error("[JsonException]:{}",exception.getMessage());
        return ApiResponse.ofException(exception);
    }

    /**
     * 统一 页面处理异常
     * @param exception PageException
     * @return 统一跳转到异常页面
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = PageException.class)
    public ModelAndView pageErrorHandler(PageException exception){
        log.error("[PageException]:{}",exception.getMessage());
        ModelAndView view = new ModelAndView();
        view.addObject("message",exception.getMessage());
        view.setViewName(DEAFULT_ERROR_VIEW);
        return view;
    }




}

