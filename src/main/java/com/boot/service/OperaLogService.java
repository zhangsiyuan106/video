package com.boot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.pojo.OperationLog;
import javassist.NotFoundException;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lm
 * @version 1.8
 * @date 2021/8/17 19:53
 */
public interface OperaLogService {

     void put(JoinPoint joinPoint, String methodName, String module, String description,String username);

     String operateContent(JoinPoint joinPoint, String methodName, String ip, HttpServletRequest request) throws ClassNotFoundException, NotFoundException;
        Map<String,Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException;


    Page<OperationLog> logListPage(int pageNo, int pageSize, String str);

    int updateLog(OperationLog log);

    int deleteLog(int id);
    void deleteAllLogByIds(int[] id);

}
