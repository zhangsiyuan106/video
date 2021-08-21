package com.boot.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.Mapper.UserMapper;
import com.boot.pojo.User;
import com.boot.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class UserServiceImpl {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenUtil tokenUtil;

    public Map<String, Object> login(User user) {
        Map<String, Object> result = new HashMap<>();
        String userName = user.getUserName();
        String userPassword = user.getUserPassword();
        // 将输入的密码使用md5加密
        String passwordMD5 = passwordMD5(userName, userPassword);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userName", userName);
        User existUser = userMapper.selectOne(wrapper);
        // 用户不存在
        if (existUser == null) {
            result.put("status", 400);
            result.put("msg", "用户名不存在");
            return result;
            //用户存在，但密码输入错误
        } else if (!existUser.getUserPassword().equals(passwordMD5)) {
            result.put("status", 400);
            result.put("msg", "密码错误");
            return result;
            //  登录成功
        } else if (existUser.getUserPassword().equals(passwordMD5)) {
            String token = tokenUtil.generateToken(new User(userName, userPassword));

            result.put("status", 200);
            result.put("msg", "登陆成功");
            result.put("id", existUser.getId());
            result.put("userName", existUser.getUserName());
            result.put("nickname", existUser.getNickname());
            result.put("avatar", existUser.getAvatar());
            result.put("backgroundImage", existUser.getBackgroundImage());
            result.put("phone", existUser.getPhone());
            result.put("email", existUser.getEmail());
            result.put("sex", existUser.getSex());
            result.put("introduction", existUser.getIntroduction());
            result.put("perms", existUser.getPerms());

            result.put("token", token);

            return result;
        }
        return result;
    }

    // md5加密
    public String passwordMD5(String userName, String userPassword) {
        // 需要加密的字符串
        String src = userName + userPassword;
        try {
            // 加密对象，指定加密方式
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 准备要加密的数据
            byte[] b = src.getBytes();
            // 加密：MD5加密一种被广泛使用的密码散列函数，
            // 可以产生出一个128位（16字节）的散列值（hash value），用于确保信息传输完整一致
            byte[] digest = md5.digest(b);
            // 十六进制的字符
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5',
                    '6', '7', 'A', 'B', 'C', 'd', 'o', '*', '#', '/'};
            StringBuffer sb = new StringBuffer();
            // 处理成十六进制的字符串(通常)
            // 遍历加密后的密码，将每个元素向右位移4位，然后与15进行与运算(byte变成数字)
            for (byte bb : digest) {
                sb.append(chars[(bb >> 4) & 15]);
                sb.append(chars[bb & 15]);
            }
            // 打印加密后的字符串
//            System.out.println(sb);
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String register(User user) {
        String userName = user.getUserName();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userName", userName);
        User existUser = userMapper.selectOne(wrapper);
        // 用户存在
        if (existUser != null) {
            return "error";
        }
        String userPassword = user.getUserPassword();
        String passwordMD5 = passwordMD5(userName, userPassword);
        User newUser = new User();
        newUser.setUserName(userName).setUserPassword(passwordMD5);
        newUser.setPerms("普通用户");
        userMapper.insert(newUser);
        return "ok";
    }

    public String register2(Map<Object, Object> map) {
        Map<String, String> values = (Map<String, String>) map.get("values");
        String userName = values.get("username");
        String password = values.get("password");
        String password2 = values.get("password2");
        String phone = values.get("phone");
        String email = values.get("email");
        String code = values.get("code");
        String verificationCode = values.get("verificationCode");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userName", userName);
        User existUser = userMapper.selectOne(wrapper);
        if (existUser != null) {
            return "用户名存在";
        }
        if (!Objects.equals(password, password2)) {
            return "两次密码不一致";
        }
        if (!Objects.equals(code, verificationCode)) {
            return "验证码错误";
        }
        String passwordMD5 = passwordMD5(userName, password);
        User newUser = new User();
        newUser.setUserName(userName).setUserPassword(passwordMD5).setEmail(email).setPhone(phone);
        newUser.setPerms("普通用户");
        userMapper.insert(newUser);
        return "注册成功";
    }

    public int deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }


    public Long getUserCount() {
        return (long) userMapper.selectCount(null);
    }

    public User getUserById(Integer id) {

        return userMapper.selectById(id);
    }

//    public int updateUser(String userPassword, Integer id) {
//
//      userMapper.updateById(id);
//        return userMapper.updateUser(userPassword, id);
//    }

    public Page<User> getUserList(int pageNo, int pageSize, String str) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like((!str.equalsIgnoreCase("") && str != null), "userName", str);
        Page<User> page = new Page<>(pageNo, pageSize);
        return userMapper.selectPage(page, wrapper);
    }

    public List<User> findAll() {
        return userMapper.selectList(null);
    }

    public User getUserByName(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userName", username);
        User existUser = userMapper.selectOne(wrapper);
        return existUser;
    }

    public User getUserByNickName(String nickName){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", nickName);
        User user = userMapper.selectOne(wrapper);
        return user;
    }
}
