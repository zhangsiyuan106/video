package com.boot.config;

import com.boot.pojo.User;

import com.boot.service.Impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义Realm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserServiceImpl userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权操作
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        simpleAuthorizationInfo.addStringPermission("user:add");
        //拿到当前登录对象
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        simpleAuthorizationInfo.addStringPermission(principal.getPerms());
        return simpleAuthorizationInfo;
    }
//认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //点击登录 执行该方法

        //用户名 密码 数据库取
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        User user = userService.getUserByName(usernamePasswordToken.getUsername());
        if (user == null){
            return null;//自动抛出异常 用户名不存在
        }
        return new SimpleAuthenticationInfo(user,user.getUserPassword(),"");
    }
}
