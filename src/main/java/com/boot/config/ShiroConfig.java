package com.boot.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

//shiro 配置
@Configuration
public class ShiroConfig {
    //shiro三大对象配置
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
            anon 无需认证就能访问
            authc 必须认证
            user  必须拥有记住我功能
            perms 对某个资源的权限才能访问
            role 拥有某个角色权限
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
        //权限访问 必须带有user:add  401 错误 未授权  会执行doGetAuthorizationInfo
//        filterMap.put("/api/user/login","perms[admin]");
//        filterMap.put("/user/update","authc");

       filterMap.put("/log/adminLogin","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/log/adminLogin"); //没有权限跳转登录页面
        return shiroFilterFactoryBean;
    }

    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm); //关联userRealm
        return securityManager;
    }
    //Realm 自定义类  放置权限操作
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
