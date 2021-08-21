package com.boot.controller.lmcontroller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.boot.Mapper.UserMapper;
import com.boot.constant.LexJSONResult;
import com.boot.pojo.MD5Utils;
import com.boot.pojo.User;
import com.boot.service.Impl.UserServiceImpl;
import com.boot.utils.OperaLog;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author memgzong
 * @create 2021/5/11
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理")
public class UserListController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;





    @OperaLog(module = "后台管理",description = "后台登录")
    @RequestMapping("/adminLogin/{username}/{pwd}")
    public LexJSONResult login(@PathVariable("username") String username,@PathVariable("pwd") String pwd) {
        Subject subject = SecurityUtils.getSubject();
        String passwordMD5 = MD5Utils.passwordMD5(username,pwd);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, passwordMD5);
        subject.login(usernamePasswordToken);
        User existUser = userService.getUserByName(username);


        return LexJSONResult.ok(existUser);
    }

    @GetMapping("/getUpdateUser/{id}")
    public String getUpdateUser(@PathVariable("id") Integer id){
        User updateUser = userService.getUserById(id);
        String jsonString = JSON.toJSONString(updateUser);
        return jsonString;
    }
    @OperaLog(module = "后台管理",description = "更新用户")

    @PostMapping("/updateUser/{username}")
    public String updateUser(@RequestBody User user,@PathVariable("username")String username){
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId());
        wrapper.set("perms", user.getPerms());
        wrapper.set("nickname",user.getNickname());
        int update = userMapper.update(null, wrapper);
        if (update <= 0 ){
            return "error";
        }
        return "success";
    }
    @OperaLog(module = "后台管理",description = "删除用户")
    @DeleteMapping("/deleteUser/{id}/")
    public String deleteUser(@PathVariable("id") Integer id){
        int deleteUser = userService.deleteUser(id);

        if (deleteUser <=0 ){
            return "error";
        }
        return "success";
    }
    @GetMapping("/getUserCount")
    public LexJSONResult getUserCount(){
        Long userCount = userService.getUserCount();
        return LexJSONResult.ok(userCount);
    }
}
