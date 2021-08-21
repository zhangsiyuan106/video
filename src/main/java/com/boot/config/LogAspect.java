package com.boot.config;


import com.boot.pojo.User;
import com.boot.service.Impl.OperaLogServiceImpl;
import com.boot.service.OperaLogService;
import com.boot.utils.OperaLog;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * 日志切面处理类
 *
 * @author Yaming
 * @create 2019-01-24
 */
@Aspect
@Component
public class LogAspect {


    @Autowired
    private OperaLogService operaLogService;
    /**
     * 日志切入点
     */
    @Pointcut("@annotation(com.boot.utils.OperaLog)")
    public void logPointCut(){}

    @AfterReturning(pointcut = "logPointCut()")
    public void doAfter(JoinPoint joinPoint){
        /**
         * 解析Log注解
         */
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        System.out.println(principal.getUserName());
        String methodName = joinPoint.getSignature().getName();
        Method method = currentMethod(joinPoint,methodName);
        OperaLog log = method.getAnnotation(OperaLog.class);

        operaLogService.put(joinPoint,methodName,log.module(),log.description(),principal.getUserName());
    }

    /**
     * 获取当前执行的方法
     *
     * @param joinPoint  连接点
     * @param methodName 方法名称
     * @return 方法
     */
    private Method currentMethod(JoinPoint joinPoint, String methodName) {
        /**
         * 获取目标类的所有方法，找到当前要执行的方法
         */
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

}