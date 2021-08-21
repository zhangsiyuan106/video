package com.boot.controller.hswcontroller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.Mapper.UserMapper;
import com.boot.constant.LexJSONResult;
import com.boot.pojo.Picture;
import com.boot.pojo.User;
import com.boot.service.Impl.PictureServiceImpl;
import com.boot.service.Impl.UserServiceImpl;
import com.boot.utils.FastDFSClient;
import com.boot.utils.NickName;
import com.boot.utils.OperaLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hsw
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PictureServiceImpl pictureService;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private UserMapper userMapper;


    @ResponseBody
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
//        System.out.println(user);
        return userService.login(user);
    }

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody User user) {
//        System.out.println(user);
        return userService.register(user);
    }
    @ResponseBody
    @PostMapping("/register2")
    public String register2(@RequestBody Map<Object,Object> map) {
        return userService.register2(map);
    }

    @ResponseBody
    @PostMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> map) throws IOException {
        System.out.println(file);
        String s = fastDFSClient.uploadFile(file);
        System.out.println(s);
        Picture picture = new Picture(null, s, null);
        pictureService.save(picture);
        Integer id = Integer.valueOf(map.get("id").toString());

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("avatar", s);
        userMapper.update(null, wrapper);
        return s;
    }

    @ResponseBody
    @PostMapping("/uploadBackgroundImage")
    public String uploadBackgroundImage(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> map) throws IOException {
        System.out.println(file);
        String s = fastDFSClient.uploadFile(file);
        System.out.println(s);
        Picture picture = new Picture(null, s, null);
        pictureService.save(picture);
        Integer id = Integer.valueOf(map.get("id").toString());

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("backgroundImage", s);
        userMapper.update(null, wrapper);
        return s;
    }

    @RequestMapping("/getPhoto")
    public LexJSONResult getPhoto(@RequestParam("id") Integer userId) {
        User userById = userService.getUserById(userId);
        return LexJSONResult.ok(userById.getAvatar());
    }

    @ResponseBody
    @PostMapping("/editNickname")
    public String editNickname(@RequestBody Map<String, Object> map) {
        Integer id = Integer.valueOf(map.get("id").toString());
        String nickname = map.get("nickname").toString();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("nickname", nickname);
        userMapper.update(null, wrapper);
        return "ok";
    }

    @ResponseBody
    @PostMapping("/editIntroduction")
    public String editIntroduction(@RequestBody Map<String, Object> map) {
        Integer id = Integer.valueOf(map.get("id").toString());
        String introduction = map.get("introduction").toString();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("introduction", introduction);
        userMapper.update(null, wrapper);
        return "ok";
    }

    //随机生成姓名
    @GetMapping("getName")
    public String getName() {
        String nickname = NickName.generateName();
        return nickname;
    }

    @PostMapping("editSex")
    public String editSex(@RequestBody Map<String, Object> map) {
        Integer id = Integer.valueOf(map.get("id").toString());
        String sex = map.get("sex").toString();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("sex", sex);
        userMapper.update(null, wrapper);
        return "ok";
    }

    @PostMapping("editPhone")
    public String editPhone(@RequestBody Map<String, Object> map) {
        Integer id = Integer.valueOf(map.get("id").toString());
        String phone = map.get("phone").toString();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("phone", phone);
        userMapper.update(null, wrapper);
        return "ok";
    }

    //查询密码是否一致
    @PostMapping("changePassword")
    public String changePassword(@RequestBody Map<String, Object> map) {
        Integer id = Integer.valueOf(map.get("id").toString());
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        User user = userMapper.selectById(id);
        String passwordMD5 = userService.passwordMD5(username, password);
        if (passwordMD5.equals(user.getUserPassword())) {
            return "1";
        } else {
            return "2";
        }
    }
    //修改密码
    @PostMapping("changePassword1")
    public String changePassword1(@RequestBody Map<String, Object> map) {
        Integer id = Integer.valueOf(map.get("id").toString());
        String password = map.get("password").toString();
        String username = map.get("username").toString();
        String passwordMD5 = userService.passwordMD5(username, password);
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("userPassword", passwordMD5);
        userMapper.update(null, wrapper);
        return "ok";
    }
    @OperaLog(module = "用户管理",description = "查看用户列表")

    @GetMapping("/userList")
    public  String listUser(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize,
                            @RequestParam("str") String str){
        String flag = "error";
        Page<User> userList = userService.getUserList(pageNo, pageSize,str);
        List<User> allUser = userList.getRecords();
        long total = userList.getTotal();
        Map<String,Object> map= new HashMap<>();
        if (allUser != null){
            flag = "ok";

        }
        if (allUser == null){
            flag = "没找到信息";
        }
        map.put("flag",flag);
        map.put("allUserList",allUser);

        map.put("totalElements",total);
        String jsonString = JSON.toJSONString(map);
        return jsonString;
    }

}
