package com.boot.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.Mapper.OperaLogRepository;
import com.boot.pojo.OperationLog;
import com.boot.service.OperaLogService;
import com.boot.utils.IPUtils;
import com.boot.utils.OperaLog;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lm
 * @version 1.8
 * @date 2021/8/17 19:53
 */
@Service
@Slf4j
public class OperaLogServiceImpl implements OperaLogService {


    @Autowired
    private OperaLogRepository operaLogRepository;
    private static final String LOG_CONTENT = "[类名]:%s,[方法]:%s,[参数]:%s,[IP]:%s";

    /**
     * 记录操作的日志
     * @param joinPoint
     * @param methodName
     * @param module
     * @param description
     */
    @Override
    public void put(JoinPoint joinPoint, String methodName, String module, String description,String username) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            OperationLog log = new OperationLog();
            if (StringUtils.isEmpty(username)) {
                username =  "未知用户";
            }
            String ip = IPUtils.getIpAddr(request);
            log.setUsername(username);
            log.setModule(module);
            log.setCreateAt(new Date());
            log.setDescription(description);
            log.setIp(ip);
            log.setContent(operateContent(joinPoint, methodName, ip, request));
            operaLogRepository.insert(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询所有日志
     * @param
     * @param request
     * @return
     */
//    public PageDto findAll(int pageNum, HttpServletRequest request) {
//        Sort sort = new Sort(Sort.Direction.DESC, "updateAt");
//        List<SpecificationOperator> operators = HttpRequests.getParametersStartingWith(request, "Q_");
//        //增加删除标识的过滤
//        SpecificationOperator isValid = new SpecificationOperator("able", "1", "EQ");
//        operators.add(isValid);
//        MySpecification<Log> mySpecifications = new MySpecification<>(operators);
//        Pageable pageable = new PageRequest(pageNum, 10, sort);
//        Page<Log> page = logRepository.findAll(mySpecifications, pageable);
//        //设置PageDto
//        List<Log> parameters = page.getContent();
//        long total = page.getTotalElements();
//        PageDto pageDto = new PageDto();
//        pageDto.setRows(parameters);
//        pageDto.setTotal(total);
//        return pageDto;
//    }
    @Override
    public String operateContent(JoinPoint joinPoint, String methodName, String ip, HttpServletRequest request) throws ClassNotFoundException, NotFoundException {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        Map<String,Object > nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName,params);
        StringBuffer bf = new StringBuffer();
        if (!CollectionUtils.isEmpty(nameAndArgs)){
            Iterator it = nameAndArgs.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = JSONObject.toJSONString(entry.getValue());
                bf.append(key).append("=");
                bf.append(value).append("&");
            }
        }
        if (StringUtils.isEmpty(bf.toString())){
            bf.append(request.getQueryString());
        }
        return String.format(LOG_CONTENT, className, methodName, bf.toString(), ip);
    }
    @Override
    public Map<String,Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String,Object > map=new HashMap<String,Object>();

        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
            return map;
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++){
            map.put( attr.variableName(i + pos),args[i]);//paramNames即参数名
        }
        return map;
    }



    @Override
    public Page<OperationLog> logListPage(int pageNo, int pageSize, String str) {
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.like((!str.equalsIgnoreCase("") && str !=null), "username", str);
        Page<OperationLog> page = new Page<>(pageNo, pageSize);
        return  operaLogRepository.selectPage(page, wrapper);

    }

    @Override
    public int updateLog(OperationLog log) {
        return operaLogRepository.insert(log);
    }

    @Override
    public int deleteLog(int id) {
        int i = operaLogRepository.deleteById(id);
        return i;
    }

    @Override
    public void deleteAllLogByIds(int[] id) {
        for (int i = 0; i < id.length; i++) {
            operaLogRepository.deleteById(id[i]);

        }
    }
}
